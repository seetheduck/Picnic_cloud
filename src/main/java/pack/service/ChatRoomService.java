package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.ChatRoomDto;
import pack.entity.ChatRoomEntity;
import pack.repository.ChatRoomRepository;
import pack.repository.FleamarketRepository;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private FleamarketRepository fleamarketRepository;


    @Transactional
    public ChatRoomDto createChatRoom(Integer fleaMarketNo, String buyerId) {
        try {
            // 채팅방 번호 계산
            Integer maxChatRoomNo = chatRoomRepository.findMaxChatRoomNo();

            // maxChatRoomNo가 null이면 0으로 초기화
            if (maxChatRoomNo == null) {
                maxChatRoomNo = 0;
            }

            // 채팅방 엔티티 생성
            ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                    .no(maxChatRoomNo + 1)  // 새 채팅방 번호
                    .fleaMarketNo(fleaMarketNo)
                    .buyerId(buyerId)
                    .sellerId(fleamarketRepository.findByNo(fleaMarketNo).toDto().getUserId())
                    .build();

            // 채팅방 저장
            ChatRoomEntity savedEntity = chatRoomRepository.save(chatRoomEntity);
            return ChatRoomEntity.toDto(savedEntity);
        } catch (Exception e) {
            e.printStackTrace();  // 예외를 콘솔에 출력
            throw new RuntimeException("채팅방 생성 중 오류 발생", e);  // 예외를 다시 던지기
        }
    }

}
