package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.ChatRoomListDto;
import pack.dto.MessageDto;
import pack.entity.ChatRoomEntity;
import pack.entity.ChatRoomListEntity;
import pack.repository.ChatRoomListRepository;
import pack.repository.ChatRoomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomListService {

    @Autowired
    private ChatRoomListRepository chatRoomListRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private MessageService messageService;

    @Transactional //채팅방 생성 시 채팅목록에 등록
    public void addChatRoomToUser(Integer chatRoomNo) {
        // 채팅방 정보를 조회
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomNo)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        String sellerId = chatRoomEntity.getSellerId();
        String buyerId = chatRoomEntity.getBuyerId();

        // 판매자와 구매자 각각의 채팅방 목록에 중복되지 않도록 추가
        addChatRoomListIfNotExists(sellerId, chatRoomNo);
        addChatRoomListIfNotExists(buyerId, chatRoomNo);
    }

    private void addChatRoomListIfNotExists(String userId, Integer chatRoomNo) {
        // 중복된 채팅방 목록이 존재하는지 확인
        boolean exists = chatRoomListRepository.existsByUserIdAndChatRoomNo(userId, chatRoomNo);
        if (!exists) {
            // 현재 가장 큰 no 값을 조회
            int newNo = chatRoomListRepository.findMaxNo() + 1;

            ChatRoomListEntity chatRoomListEntity = ChatRoomListEntity.builder()
                    .no(newNo)
                    .userId(userId)
                    .chatRoomNo(chatRoomNo)
                    .build();
            chatRoomListRepository.save(chatRoomListEntity);
        }
    }

    //해당 유저의 채팅 목록 조회
    public List<ChatRoomListDto> getChatRoomListByUserId(String userId) {
        // 사용자 ID로 채팅방 리스트 조회
        List<ChatRoomListEntity> chatRoomListEntities = chatRoomListRepository.findByUserId(userId);

        // DTO 변환
        return chatRoomListEntities.stream()
                .map(entity -> {
                    ChatRoomListDto dto = ChatRoomListDto.builder()
                            .no(entity.getNo())
                            .userId(entity.getUserId())
                            .chatRoomNo(entity.getChatRoomNo())
                            .build();

                    // 채팅방 번호로 마지막 메시지와 시간을 가져옴
                    MessageDto latestMessage = messageService.findLatestMessageByChatRoomNo(entity.getChatRoomNo());
                    if (latestMessage != null) {
                        dto.setLastMessage(latestMessage.getMessageContents());
                        dto.setLastMessageTime(latestMessage.getCreateDate().toString());

                        String otherPartyId = latestMessage.getSenderId().equals(userId)
                                ? getOtherPartyId(entity.getChatRoomNo(), userId)
                                : latestMessage.getSenderId();

                        dto.setSenderId(otherPartyId);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    private String getOtherPartyId(Integer chatRoomNo, String currentUserId) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomNo)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        // Return the ID 다른 사람
        return chatRoomEntity.getSellerId().equals(currentUserId)
                ? chatRoomEntity.getBuyerId()
                : chatRoomEntity.getSellerId();
    }
}
