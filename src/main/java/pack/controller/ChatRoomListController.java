package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pack.dto.ChatRoomListDto;
import pack.service.ChatRoomListService;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ChatRoomListController {

    @Autowired
    private ChatRoomListService chatRoomListService;

    @GetMapping("/api/chatList/{userId}")
    public List<ChatRoomListDto> getChatRoomList(@PathVariable String userId) {
        // 사용자 ID를 기반으로 채팅방 리스트를 조회
        return chatRoomListService.getChatRoomListByUserId(userId);
    }
}
