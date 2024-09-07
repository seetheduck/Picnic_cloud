package pack.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import pack.dto.MessageDto;

@Controller
@CrossOrigin("*")
public class ChatController {
    //ChatController는 실시간 메시징(WebSocket) 관련 작업

    @MessageMapping("/chat.send/{chatRoomId}") // 클라이언트가 메시지를 전송할 경로
    @SendTo("/topic/chatRoom/{chatRoomId}") // 해당 채팅방에 연결된 사용자들에게 메시지 전송
    public MessageDto sendMessage(@DestinationVariable String chatRoomId, MessageDto message) {
        return message; // 메시지를 반환하면 해당 채팅방에 구독 중인 모든 클라이언트가 메시지를 받음
    }
}
