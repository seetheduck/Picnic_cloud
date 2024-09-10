package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.UserDto;
import pack.dto.request.EmailRequest;
import pack.dto.request.PasswordUpdateRequest;
import pack.dto.request.SignupRequest;
import pack.dto.response.FindIdResponseDto;
import pack.dto.response.LoginResponseDto;
import pack.repository.UserDetailRepository;
import pack.service.EmailService;
import pack.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDetailRepository userDetailRepository;

    // 회원가입
    @PostMapping(value = "/signup", produces = "application/json; charset=utf8")
    public ResponseEntity<Void> signup(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest.getUserDto(), signupRequest.getUserDetailDto());
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping(value = "/login", produces = "application/json; charset=utf8")
    public ResponseEntity<LoginResponseDto> login(@RequestBody UserDto userDto) {
        try {
            String token = userService.login(userDto.getId(), userDto.getPw());
            return ResponseEntity.ok(new LoginResponseDto(token, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponseDto(null, e.getMessage()));
        }
    }

    // ID 찾기
    @PostMapping("/find-id")
    public ResponseEntity<FindIdResponseDto> findId(@RequestBody EmailRequest emailRequest) {
        try {
            String userId = userService.findIdByEmail(emailRequest.getEmail());
            return ResponseEntity.ok(new FindIdResponseDto(userId, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new FindIdResponseDto(null, e.getMessage()));
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody EmailRequest emailRequest) {
        try {
            // 서비스 메소드 호출
            boolean isEmailSent = userService.processPasswordReset(emailRequest.getEmail());

            if (isEmailSent) {
                return ResponseEntity.ok().body("비밀번호 재설정 링크가 성공적으로 전송되었습니다.");
            } else {
                // 이메일이 데이터베이스에 존재하지 않는 경우
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 이메일의 계정이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();  // 스택 트레이스를 로그에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    }

    @GetMapping("/reset-password")
    public ResponseEntity<String> getResetPasswordPage(@RequestParam("token") String token) {
        boolean isValid = userService.validateToken(token);
        if (isValid) {
            // 유효한 토큰이면 비밀번호 재설정 페이지로 이동 (프론트엔드와 연동)
            return ResponseEntity.ok("비밀번호를 새로 설정할 수 있는 페이지로 이동합니다.");
        } else {
            // 유효하지 않은 토큰이면 에러 응답
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 토큰입니다.");
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestParam("token") String encryptedToken, @RequestBody PasswordUpdateRequest request) {
        boolean isValid = userService.validateToken(encryptedToken);
        if (isValid) {
            // 비밀번호를 새로 설정
            userService.updatePassword(encryptedToken, request.getNewPassword());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 유저 비활성화
    @PostMapping("/deactivate")
    public ResponseEntity<Void> deactivateAccount(@RequestBody UserDto userDto) {
        Integer no = userDto.getNo();
        userService.deactivateAccount(no);
        return ResponseEntity.ok().build();
    }

    // 유저 활성화
    @PostMapping("/reactivate")
    public ResponseEntity<Void> reactivateAccount(@RequestBody UserDto userDto) {
        Integer no = userDto.getNo();
        userService.reactivateAccount(no);
        return ResponseEntity.ok().build();
    }

}