package pack.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.ChatRoomListDto;
import pack.service.ChatRoomListService;
import pack.service.JwtService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ChatRoomListController {

    @Autowired
    private ChatRoomListService chatRoomListService;

    private static final String USER_ID_ATTRIBUTE = "userId";
    @Autowired
    private JwtService jwtService;

    @GetMapping("/chatList")
    public List<ChatRoomListDto> getChatRoomList(HttpServletRequest request) {

        Object userIdAttribute = request.getAttribute(USER_ID_ATTRIBUTE);
        if (userIdAttribute == null) {
            throw new RuntimeException("User ID attribute not found in request");
        }

        String userId = userIdAttribute.toString();
        try {
            // 사용자 ID로 채팅 목록을 조회
            return chatRoomListService.getChatRoomListByUserId(userId);
        } catch (Exception e) {
            // 예외 처리 및 로그 기록
            throw new RuntimeException("Error fetching chat room list", e);
        }
    }

    @DeleteMapping("/leave/{chatRoomNo}")
    public ResponseEntity<Object> leaveChatRoom(HttpServletRequest request, @PathVariable("chatRoomNo") int chatRoomNo) {
        try {
            // `Authorization` 헤더에서 JWT 추출
            String token = request.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Authorization 헤더가 없거나 형식이 올바르지 않습니다.");
            }

            String userId = jwtService.getUserFromToken(token.replace("Bearer ", ""));
            if (userId == null) {
                return ResponseEntity.status(401).body("사용자 ID를 추출할 수 없습니다.");
            }

            // 서비스에 사용자 ID와 채팅방 번호를 전달하여 나가기 처리
            chatRoomListService.leaveChatRoom(userId, chatRoomNo);

            return ResponseEntity.ok("채팅방에서 성공적으로 나갔습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 내부 오류가 발생했습니다.");
        }
    }

}
