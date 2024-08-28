package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.entity.UserEntity;
import pack.repository.UserRepository;

@Service
public class LoginService {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private JwtService jwtService;  // JWT 생성 및 검증 서비스

	    public String login(String id, String password) {
	        UserEntity user = userRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        if (!user.getPw().equals(password)) {
	            throw new RuntimeException("Invalid password");
	        }

	        // JWT 토큰 생성
	        return jwtService.createToken(user.getId());
	    }
}
