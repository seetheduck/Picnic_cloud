package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pack.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    void deleteByToken(String token);
}