package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.SignupRequest;
import pack.dto.UserMasterDto;
import pack.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest.getUserMasterDto(), signupRequest.getUserDetailDto());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserMasterDto userMasterDto) {
        String token = userService.login(userMasterDto.getId(), userMasterDto.getPw());
        return ResponseEntity.ok(token);
    }
    
    @PostMapping("/deactivate")
    public ResponseEntity<Void> deactivateAccount(@RequestParam("no") Integer no) {
        userService.deactivateAccount(no);
        return ResponseEntity.ok().build();
    }
    
}