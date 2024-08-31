package pack.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {

    private String secretKey = "mySecretKey"; // 비밀 키 (실제 프로젝트에서는 안전하게 관리해야 함)
    private long validityInMilliseconds = 3600000; // 1시간

    // 토큰 생성
    public String generateToken(String username) {
        Claims claims = Jwts.claims().setSubject(username); // JWT의 claims (사용자 이름 저장)
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds); // 유효 기간 설정
        return "";
    }
}