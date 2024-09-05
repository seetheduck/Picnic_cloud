package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.UserDetailDto;
import pack.dto.UserDto;
import pack.entity.UserDetailEntity;
import pack.entity.UserEntity;
import pack.repository.CustomPasswordEncoder;
import pack.repository.UserDetailRepository;
import pack.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private  CustomPasswordEncoder passwordEncoder;

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
        UserEntity user = userRepository.findById(id);

        if (user == null) {
            throw new IllegalArgumentException("Invalid user");
        }

        if (Boolean.TRUE.equals(user.getAccountDeleteIs())) {
            throw new IllegalArgumentException("이 계정은 비활성화 되었습니다. 다시 활성화 하시겠습니까??");
        }

        if (!passwordEncoder.matches(pw, user.getPw())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return jwtService.createToken(id);  // JWT 토큰 생성

    }

    @Transactional
    public void deactivateAccount(Integer no) {
        // 사용자 ID로 UserMasterEntity를 조회
        UserEntity user = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // UserDetailEntity를 조회
        UserDetailEntity userDetail = userDetailRepository.findById(user.getNo())
                .orElseThrow(() -> new IllegalArgumentException("User details not found"));

        // 계정 비활성화
        user.setAccountDeleteIs(true);
        userDetail.setAccountDeleteDate(LocalDateTime.now());  // 비활성화 날짜 설정
        userRepository.save(user);
        userDetailRepository.save(userDetail);
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
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity != null) {
            return userEntity;
        } else {
            // 필요한 경우, 사용자를 찾지 못했을 때의 처리
            throw new RuntimeException("User not found with id: " + id);
        }
    }

}

