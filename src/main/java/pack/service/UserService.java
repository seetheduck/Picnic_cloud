package pack.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.UserDetailDto;
import pack.dto.UserMasterDto;
import pack.entity.UserDetailEntity;
import pack.entity.UserMasterEntity;
import pack.repository.UserDetailRepository;
import pack.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;
    
    @Autowired
    private JwtService jwtService;

    private final CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();

    public void checkDuplicateId(String id) {
        if (userRepository.existsById(id)) {
            throw new IllegalArgumentException("ID already exists");
        }
    }
    
    public void signup(UserMasterDto userDto, UserDetailDto detailDto) {
    	
    	// ID 중복 검사
        checkDuplicateId(userDto.getId());
    	
        // 비밀번호 해시화
        userDto.setPw(passwordEncoder.encode(userDto.getPw()));
        
        // 현재 가장 큰 no 값 조회
        int maxNo = userRepository.findMaxNo();
        int newNo = maxNo + 1;
        
        // UserMaster에 no 값 설정
        userDto.setNo(newNo);
        UserMasterEntity userMaster = userDto.toEntity();
        UserMasterEntity savedUserMaster = userRepository.save(userMaster);

        // UserDetail에 동일한 no 값 설정
        detailDto.setNo(newNo);
        UserDetailEntity userDetail = detailDto.toEntity();
        userDetailRepository.save(userDetail);
    }

    public String login(String id, String pw) {
        UserMasterEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        // 비밀번호 검증
        if (passwordEncoder.matches(pw, user.getPw())) {
            // 비밀번호가 일치하면 JWT 토큰 생성 및 반환
            return jwtService.createToken(id);  // JWT 토큰 생성
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }
    
    public void deactivateAccount(Integer no) {
        // 사용자 ID로 UserMasterEntity를 조회
        UserMasterEntity user = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // UserDetailEntity를 조회
        UserDetailEntity userDetail = userDetailRepository.findById(user.getNo())
                .orElseThrow(() -> new IllegalArgumentException("User details not found"));

        // 계정 비활성화
        user.setSignoutIs(true);
        userDetail.setSignoutDate(LocalDateTime.now());  // 비활성화 날짜 설정
        userRepository.save(user);
        userDetailRepository.save(userDetail);
    }
    
}
