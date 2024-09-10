package pack.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            messageHelper.setTo(toEmail);
            messageHelper.setSubject("비밀번호 재설정 요청");

            // HTML 이메일 본문 설정
            String htmlBody = "<html><body>"
                    + "<p>안녕하세요, Picnic Cloud 입니다. 😍</p>"
                    + "<p>비밀번호를 재설정하려면 아래 링크를 클릭하세요:</p>"
                    + "<p><a href=\"" + resetLink + "\">비밀번호 재설정 링크</a></p>"
                    + "<p>이 링크는 1시간 동안 유효합니다.</p>"
                    + "<p>감사합니다.</p>"
                    + "</body></html>";

            messageHelper.setText(htmlBody, true);

            // 이메일 전송
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            // 로그를 남기거나 예외를 처리하세요.
        }
    }
}
