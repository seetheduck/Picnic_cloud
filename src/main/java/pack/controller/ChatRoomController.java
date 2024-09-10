package pack.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.ChatRoomDto;
import pack.dto.request.ChatRoomRequest;
import pack.service.ChatRoomService;
import pack.service.ChatRoomListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pack.service.JwtService;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatRoomController {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomListService chatRoomListService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<Object> createChatRoom(HttpServletRequest request,
                                                 @RequestBody ChatRoomRequest chatRoomRequest) {
        try {
            Integer fleaMarketNo = chatRoomRequest.getFleaMarketNo();
            String token = request.getHeader("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                logger.error("Authorization 헤더가 없거나 형식이 올바르지 않습니다.");
                return ResponseEntity.badRequest().body("Authorization 헤더가 누락되었거나 형식이 올바르지 않습니다.");
            }

            String buyerId = jwtService.getUserFromToken(token.replace("Bearer ", ""));

            if (buyerId == null) {
                logger.error("구매자 ID를 추출할 수 없습니다.");
                return ResponseEntity.status(401).body("구매자 ID를 추출할 수 없습니다.");
            }

            if (fleaMarketNo == null) {
                logger.error("게시판 번호가 없습니다.");
                return ResponseEntity.badRequest().body("게시판 번호가 누락되었습니다.");
            }

            ChatRoomDto chatRoomDto = chatRoomService.createChatRoom(fleaMarketNo, buyerId);

            if (chatRoomDto == null) {
                return ResponseEntity.status(500).body("채팅방을 만들 수 없습니다.");
            }

            chatRoomListService.addChatRoomToUser(chatRoomDto.getNo());
            return ResponseEntity.ok(chatRoomDto);

        } catch (Exception e) {
            logger.error("채팅방 생성 중 오류 발생", e);
            return ResponseEntity.status(500).body("서버 내부 오류가 발생했습니다.");
        }
    }
}
