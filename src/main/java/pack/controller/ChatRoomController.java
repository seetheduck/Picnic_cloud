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

    @PostMapping("/create") //(게시판에서 채팅 버튼 클릭시)
    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomDto request) {
        try {
            //해당 게시판 번호가 없는 경우
            if (request.getFleaMarketNo() == null || request.getBuyerId() == null) {
                logger.error("채팅방이 사라졌습니다.");
                return ResponseEntity.badRequest().body("Invalid request data");
            }

            // 채팅방 생성
            ChatRoomDto chatRoomDto = chatRoomService.createChatRoom(request.getFleaMarketNo(), request.getBuyerId());

            if (chatRoomDto == null) {
                return ResponseEntity.status(500).body("채팅방을 만들 수 없습니다.");
            }
            
            //chatRoomList에 목록 생성
            chatRoomListService.addChatRoomToUser(chatRoomDto.getNo());

            //성공이면 반환
            return ResponseEntity.ok(chatRoomDto);

        } catch (Exception e) {
            // 예외 발생 시 로그 기록
            logger.error("Exception during chat room creation", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
