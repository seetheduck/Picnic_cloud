package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.request.SignupRequest;
import pack.dto.UserDto;
import pack.dto.request.EmailRequest;
import pack.dto.response.FindIdResponseDto;
import pack.dto.response.LoginResponseDto;
import pack.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;
    
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