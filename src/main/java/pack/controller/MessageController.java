package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.BoardAndMessagesDto;
import pack.dto.MessageDto;
import pack.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin("*")
public class MessageController {
    //ChatController랑 다름
    //MessageController는 데이터베이스에 메시지를 저장

    @Autowired
    private MessageService messageService;

    // 메시지 전송 메서드
    @PostMapping("/send")
    public MessageDto sendMessage(@RequestBody MessageDto messageDto) {
        return messageService.saveMessage(messageDto);
    }

    // 특정 채팅방의 메시지를 조회
//    @GetMapping("/{chatRoomId}")
//    public List<MessageDto> getMessagesByChatRoomId(@PathVariable Integer chatRoomId) {
//        return messageService.getMessagesByChatRoomId(chatRoomId);
//    }
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<BoardAndMessagesDto> getMessagesAndBoard(@PathVariable Integer chatRoomId) {
        BoardAndMessagesDto result = messageService.getMessagesAndBoardByChatRoomId(chatRoomId);
        return ResponseEntity.ok(result);
    }
}
