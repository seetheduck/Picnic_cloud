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

    @GetMapping("/chatList")
    public List<ChatRoomListDto> getChatRoomList(HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        return chatRoomListService.getChatRoomListByUserId(userId);
    }
}
