package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.ChatRoomDto;
import pack.dto.ChatRoomListDto;
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
                .map(entity -> ChatRoomListDto.builder()
                        .no(entity.getNo())
                        .userId(entity.getUserId())
                        .chatRoomNo(entity.getChatRoomNo())
                        .build())
                .collect(Collectors.toList());
    }
}
