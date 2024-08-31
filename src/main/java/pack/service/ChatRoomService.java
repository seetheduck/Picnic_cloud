package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.ChatRoomDto;
import pack.entity.ChatRoomEntity;
import pack.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatRoomDto createChatRoom(Integer fleaMarketNo, String buyerId, String sellerId) {
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .fleaMarketNo(fleaMarketNo)
                .buyerId(buyerId)
                .sellerId(sellerId)
                .build();

        ChatRoomEntity savedEntity = chatRoomRepository.save(chatRoomEntity);
        return ChatRoomEntity.toDto(savedEntity);
    }
}
