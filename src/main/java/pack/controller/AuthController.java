package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.SignupRequest;
import pack.dto.UserDto;
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
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        try {
            String token = userService.login(userDto.getId(), userDto.getPw());
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            if ("이 계정은 비활성화 되었습니다. 다시 활성화 하시겠습니까??".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
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