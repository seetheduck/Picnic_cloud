package pack.dto.request;

import lombok.Data;

@Data
public class PasswordUpdateRequest {
    private String newPassword;
    private String email; // 이메일을 토큰과 함께 삭제할 필요가 있으므로 포함
}