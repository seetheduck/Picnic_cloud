package pack.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pack.dto.ChatRoomListDto;
import pack.service.ChatRoomListService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ChatRoomListController {

    @Autowired
    private ChatRoomListService chatRoomListService;

    private static final String USER_ID_ATTRIBUTE = "userId";

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
}
