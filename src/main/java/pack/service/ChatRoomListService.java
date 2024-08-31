package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pack.entity.ChatRoomListEntity;
import pack.repository.ChatRoomListRepository;

@Service
public class ChatRoomListService {

    @Autowired
    private ChatRoomListRepository chatRoomListRepository;

    @Transactional
    public void addChatRoomToUser(Integer userId, Integer chatRoomId) {
        ChatRoomListEntity chatRoomListEntity = ChatRoomListEntity.builder()
                .userId(userId)
                .chatRoomId(chatRoomId)
                .build();

        chatRoomListRepository.save(chatRoomListEntity);
    }
}
