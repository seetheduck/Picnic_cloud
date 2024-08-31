package pack.handler;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatHandler extends TextWebSocketHandler {

    private final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer chatRoomId = (Integer) session.getAttributes().get("chatRoomId");
        sessions.put(chatRoomId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        Integer chatRoomId = (Integer) session.getAttributes().get("chatRoomId");
        for (WebSocketSession s : sessions.values()) {
            if (s.isOpen() && s.getAttributes().get("chatRoomId").equals(chatRoomId)) {
                s.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer chatRoomId = (Integer) session.getAttributes().get("chatRoomId");
        sessions.remove(chatRoomId);
    }
}
