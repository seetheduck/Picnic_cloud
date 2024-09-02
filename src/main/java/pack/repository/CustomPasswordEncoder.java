package pack.repository;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {
	 // 비밀번호를 SHA-256으로 해시화
    public String encode(String rawPassword) {
        return DigestUtils.sha256Hex(rawPassword);
    }

    // 원시 비밀번호와 해시화된 비밀번호를 비교
    public boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
