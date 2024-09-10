package pack.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.BoardAndMessagesDto;
import pack.dto.MessageDto;
import pack.service.JwtService;
import pack.service.MessageService;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private JwtService jwtService;

//    private static final String USER_ID_ATTRIBUTE = "userId";
    // 메시지 전송 메서드
    @PostMapping("/send")
    public ResponseEntity<MessageDto> sendMessage(HttpServletRequest request, @RequestBody MessageDto messageDto) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                logger.error("Authorization 헤더가 없거나 형식이 올바르지 않습니다.");
                return ResponseEntity.badRequest().body(null);
            }

            String userId = jwtService.getUserFromToken(token.replace("Bearer ", ""));
            if (userId == null) {
                logger.error("사용자 ID를 추출할 수 없습니다.");
                return ResponseEntity.status(401).body(null);
            }

            messageDto.setSenderId(userId);
            MessageDto savedMessage = messageService.saveMessage(messageDto);
            return ResponseEntity.ok(savedMessage);

        } catch (Exception e) {
            logger.error("메시지 전송 중 오류 발생", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    // 특정 채팅방의 메시지를 조회
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<BoardAndMessagesDto> getMessagesAndBoard(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @PathVariable Integer chatRoomId) {
        try {
            System.out.println( "******#####******"+ authorizationHeader);
            // Authorization 헤더에서 토큰 추출
            String token = (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) ?
                    authorizationHeader.replace("Bearer ", "") : null;

            String userId = null;
            if (token != null) {
                // JWT 토큰 유효성 검사
                userId = jwtService.getUserFromToken(token);
                if (userId == null) {
                    logger.error("JWT 토큰 유효성 검사 실패");
                    return ResponseEntity.status(401).body(null); // 인증 실패
                }
            }
            System.out.println("***********+++++++" + userId);
            // 사용자 ID가 없거나 토큰이 유효하지 않은 경우 처리
            if (userId == null) {
                logger.error("사용자 ID가 없습니다.");
                return ResponseEntity.status(401).body(null); // 인증 실패
            }

            // 메시지 및 게시판 정보 가져오기
            BoardAndMessagesDto result = messageService.getMessagesAndBoardByChatRoomId(chatRoomId);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("메시지 및 게시판 정보 조회 중 오류 발생", e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
