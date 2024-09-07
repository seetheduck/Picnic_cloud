package pack.controller;

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

    @GetMapping("/chatList/{userId}")
    public List<ChatRoomListDto> getChatRoomList(@PathVariable String userId) {
        return chatRoomListService.getChatRoomListByUserId(userId);
    }



}
