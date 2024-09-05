package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.ChatRoomDto;
import pack.service.ChatRoomService;
import pack.service.ChatRoomListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatRoomController {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomListService chatRoomListService;

    @PostMapping("/create")
    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomDto request) {
        try {
            if (request.getFleaMarketNo() == null || request.getBuyerId() == null) {
                logger.error("Invalid request: fleaMarketNo or buyerId is missing");
                return ResponseEntity.badRequest().body("Invalid request data");
            }

            // 채팅방 생성
            ChatRoomDto chatRoomDto = chatRoomService.createChatRoom(request.getFleaMarketNo(), request.getBuyerId());
            logger.info("Chat room created: {}", chatRoomDto);

            if (chatRoomDto == null) {
                logger.error("Chat room creation failed");
                return ResponseEntity.status(500).body("Failed to create chat room");
            }

            chatRoomListService.addChatRoomToUser(chatRoomDto.getNo());
            logger.info("Chat room added to user list");// 채팅방을 채팅 목록에 추가하는 부분에서 예외가 발생할 수 있음

            // 성공적으로 응답 반환
            return ResponseEntity.ok(chatRoomDto);

        } catch (Exception e) {
            // 예외 발생 시 로그 기록
            logger.error("Exception during chat room creation", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
