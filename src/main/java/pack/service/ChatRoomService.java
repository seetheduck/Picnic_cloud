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
        //채팅방 번호
            Integer maxChatRoomNo = chatRoomRepository.findMaxChatRoomNo();
            System.out.println(maxChatRoomNo);
            if(maxChatRoomNo == null){
                maxChatRoomNo = 1;
            }

            ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                    .no(maxChatRoomNo + 1)
                    .fleaMarketNo(fleaMarketNo)
                    .buyerId(buyerId)
                    .sellerId(fleamarketRepository.findByNo(fleaMarketNo).toDto().getUserId())
                    .build();

            ChatRoomEntity savedEntity = chatRoomRepository.save(chatRoomEntity);
            return ChatRoomEntity.toDto(savedEntity);
        } catch (Exception e) {
            e.printStackTrace(); // 예외를 콘솔에 출력
            throw new RuntimeException("채팅방 생성 중 오류 발생", e); // 예외를 다시 던지기
        }
    }
}
