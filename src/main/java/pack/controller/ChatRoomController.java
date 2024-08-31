package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pack.dto.ChatRoomDto;
import pack.service.ChatRoomService;
import pack.service.ChatRoomListService;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomListService chatRoomListService;

    @PostMapping("/create")
    public ChatRoomDto createChatRoom(
            @RequestParam Integer fleaMarketNo,
            @RequestParam String buyerId,
            @RequestParam String sellerId,
            @RequestParam Integer userId) {

        ChatRoomDto chatRoomDto = chatRoomService.createChatRoom(fleaMarketNo, buyerId, sellerId);
        chatRoomListService.addChatRoomToUser(userId, chatRoomDto.getNo());
        return chatRoomDto;
    }
}
