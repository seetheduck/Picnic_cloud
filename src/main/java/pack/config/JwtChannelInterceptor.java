package pack.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import pack.service.JwtService;

@Slf4j
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {
    @Autowired
    private JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(message);
        log.info(">>>>>> headerAccessor : {}", headerAccessor);

        // STOMP CONNECT 명령어를 확인하기 위해 헤더를 직접 검사합니다.
        if (headerAccessor.getFirstNativeHeader("Authorization") != null) {
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                try {
                    String userId = jwtService.getUserFromToken(token);
                    headerAccessor.setUser(() -> userId); // 사용자 설정
                    log.info("User authenticated: {}", userId);
                } catch (Exception e) {
                    log.warn("Authentication failed", e);
                }
            } else {
                log.warn("Authorization header is missing or malformed");
            }
        }

        return message;
    }
}
