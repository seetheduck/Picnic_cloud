package pack.controller;

import jakarta.servlet.http.HttpServletRequest;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatRoomController {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomListService chatRoomListService;

    @PostMapping("/create") // 게시판에서 채팅 버튼 클릭 시
    public ResponseEntity<Object> createChatRoom(
            @RequestParam Integer fleaMarketNo,
            HttpServletRequest request) {
        try {
            // 입력 데이터 검증
            if (fleaMarketNo == null) {
                logger.error("게시판 번호가 없습니다.");
                return ResponseEntity.badRequest().body("게시판 번호가 누락되었습니다.");
            }

            String buyerId = request.getAttribute("userId").toString();
            if (buyerId == null) {
                logger.error("구매자 ID가 없습니다.");
                return ResponseEntity.badRequest().body("구매자 ID가 누락되었습니다.");
            }

            // 채팅방 생성
            ChatRoomDto chatRoomDto = chatRoomService.createChatRoom(fleaMarketNo, buyerId);

            if (chatRoomDto == null) {
                return ResponseEntity.status(500).body("채팅방을 만들 수 없습니다.");
            }

            // 채팅방 목록에 추가
            chatRoomListService.addChatRoomToUser(chatRoomDto.getNo());

            // 성공적으로 생성된 채팅방 정보 반환
            return ResponseEntity.ok(chatRoomDto);

        } catch (Exception e) {
            // 예외 발생 시 로그 기록 및 500 에러 반환
            logger.error("채팅방 생성 중 오류 발생", e);
            return ResponseEntity.status(500).body("서버 내부 오류가 발생했습니다.");
        }
    }

//    @GetMapping("/chat-room/{chatRoomId}")
//    public ResponseEntity<ChatRoomDto> getChatRoom(@PathVariable Integer chatRoomId) {
//        ChatRoomDto chatRoom = chatRoomService.getChatRoomById(chatRoomId);
//        if (chatRoom == null) {
//            return ResponseEntity.status(404).body(null);
//        }
//        return ResponseEntity.ok(chatRoom);
//    }
}
