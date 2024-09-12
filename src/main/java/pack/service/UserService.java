package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.UserDetailDto;
import pack.dto.UserDto;
import pack.entity.PasswordResetToken;
import pack.entity.UserDetailEntity;
import pack.entity.UserEntity;
import pack.repository.CustomPasswordEncoder;
import pack.repository.PasswordResetTokenRepository;
import pack.repository.UserDetailRepository;
import pack.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenEncryptionService tokenEncryptionService;

    public void checkDuplicateId(String id) {
        if (userRepository.existsById(id)) {
            throw new IllegalArgumentException("같은 ID가 존재합니다");
        }
    }

    public void signup(UserDto userDto, UserDetailDto detailDto) {
        // 필수 입력값 확인
        if (userDto.getId() == null) {
            throw new IllegalArgumentException("ID를 작성해주세요.");
        }
        if (userDto.getPw() == null) {
            throw new IllegalArgumentException("비밀번호를 작성해주세요.");
        }
        if (userDto.getName() == null) {
            throw new IllegalArgumentException("이름을 작성해주세요.");
        }
        if (detailDto.getEmail() == null) {
            throw new IllegalArgumentException("Email을 작성해주세요.");
        }


        // ID 중복 검사
        checkDuplicateId(userDto.getId());

        // 비밀번호 해시화
        userDto.setPw(passwordEncoder.encode(userDto.getPw()));

        // 현재 가장 큰 no 값 조회
        int maxNo = userRepository.findMaxNo();
        int newNo = maxNo + 1;

        // UserMaster에 no 값 설정
        userDto.setNo(newNo);
        UserEntity userMaster = userDto.toEntity();
        UserEntity savedUserMaster = userRepository.save(userMaster);

        // UserDetail에 동일한 no 값 설정
        detailDto.setNo(newNo);
        UserDetailEntity userDetail = detailDto.toEntity();
        userDetailRepository.save(userDetail);
    }

    public String login(String id, String pw) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        if (Boolean.TRUE.equals(user.getAccountDeleteIs())) {
            throw new IllegalArgumentException("이 계정은 비활성화 되었습니다. 다시 활성화 하시겠습니까?");
        }

        if (!passwordEncoder.matches(pw, user.getPw())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return jwtService.createToken(id);  // JWT 토큰 생성
    }

    // 이메일로 ID 찾기
    public String findIdByEmail(String email) {
        // 이메일로 UserDetailEntity 조회
        UserDetailEntity userDetail = userDetailRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 사용자 없음"));

        // UserDetailEntity의 no를 사용하여 UserEntity 조회
        UserEntity user = userRepository.findByNo(userDetail.getNo())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        // 사용자 ID 반환
        return user.getId();
    }

    public boolean processPasswordReset(String email) {
        // 사용자 이메일로 사용자 정보 조회
        Optional<UserDetailEntity> userDetailOptional = userDetailRepository.findByEmail(email);

        if (userDetailOptional.isPresent()) {
            // 비밀번호 재설정 토큰 생성
            String token = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

            // 암호화된 링크 생성
            String encryptedToken = tokenEncryptionService.encryptToken(token);
            String resetLink = "http://localhost:3000/#/auth/reset-password?token=" + encryptedToken;

            // 이메일 전송
            emailService.sendPasswordResetEmail(email, resetLink);

            // 토큰 저장
            savePasswordResetToken(email, token, expiryDate);

            return true; // 이메일 전송 성공
        } else {
            return false; // 이메일이 존재하지 않음
        }
    }

    // 비밀번호 재설정 토큰 저장
    public void savePasswordResetToken(String email, String token, LocalDateTime expiryDate) {
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .email(email)
                .token(token)
                .expiryDate(expiryDate)
                .build();
        tokenRepository.save(resetToken);
    }

    // 비밀번호 재설정 토큰 유효성 검사
    public boolean validateToken(String encryptedToken) {
        // 복호화된 토큰으로 비밀번호 재설정 토큰 조회
        PasswordResetToken resetToken =
                tokenRepository.findByToken(tokenEncryptionService.decryptToken(encryptedToken));
        return resetToken != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now());

    }

    // 비밀번호 업데이트
    @Transactional
    public void updatePassword(String encryptedToken, String newPassword) {
        // 암호화된 토큰을 복호화
        String token = tokenEncryptionService.decryptToken(encryptedToken);

        // 비밀번호 재설정 토큰 조회
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            String email = resetToken.getEmail();

            // 이메일로 사용자 정보 조회
            Optional<UserDetailEntity> userDetailOptional = userDetailRepository.findByEmail(email);
            if (userDetailOptional.isPresent()) {
                UserDetailEntity userDetail = userDetailOptional.get();
                Optional<UserEntity> userOptional = userRepository.findById(userDetail.getNo());
                if (userOptional.isPresent()) {
                    UserEntity user = userOptional.get();
                    String encodedPassword = passwordEncoder.encode(newPassword); // 비밀번호 암호화
                    user.setPw(encodedPassword);
                    userRepository.save(user);

                    // 비밀번호 재설정 후 토큰 삭제
                    tokenRepository.deleteByToken(token);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid or expired token.");
        }
    }

    @Transactional
    public void reactivateAccount(Integer no) {
        // UserEntity 조회
        UserEntity user = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // UserDetailEntity 조회
        UserDetailEntity userDetail = userDetailRepository.findById(user.getNo())
                .orElseThrow(() -> new IllegalArgumentException("User details not found"));

        // 계정 활성화
        user.setAccountDeleteIs(false);  // 비활성화 플래그 해제
        userDetail.setAccountDeleteDate(null);  // 비활성화 날짜 제거

        // 변경된 엔티티 저장
        userRepository.save(user);
        userDetailRepository.save(userDetail);
    }

    public UserEntity findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

}

