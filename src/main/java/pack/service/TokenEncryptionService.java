package pack.service;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenEncryptionService {

    // 암호화
    public String encryptToken(String token) {
        return Base64.getUrlEncoder().encodeToString(token.getBytes());
    }

    // 복호화
    public String decryptToken(String encryptedToken) {
        return new String(Base64.getUrlDecoder().decode(encryptedToken));
    }
}
