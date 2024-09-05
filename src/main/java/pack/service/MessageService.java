package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.MessageDto;
import pack.entity.ChatRoomEntity;
import pack.entity.MessageEntity;
import pack.repository.ChatRoomRepository;
import pack.repository.MessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Transactional
    public MessageDto saveMessage(MessageDto messageDto) {
        // 메시지가 속한 채팅방을 조회
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(messageDto.getChatRoomNo())
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        MessageEntity messageEntity = MessageDto.toEntity(messageDto,chatRoomEntity);

        //채팅방 번호 조회 (가장 마지막)
        Integer maxMassegeNo = messageRepository.findMaxMessageNo();
        if(maxMassegeNo == null) {
            maxMassegeNo = 1;
        }
        messageEntity.setNo(maxMassegeNo + 1);
        
        //채팅방 등록
        MessageEntity savedEntity = messageRepository.save(messageEntity);
        return MessageEntity.toDto(savedEntity);
    }

    // 특정 채팅방의 메시지 조회 메서드
    public List<MessageDto> getMessagesByChatRoomId(Integer chatRoomId) {
        List<MessageEntity> messageEntities = messageRepository.findByChatRoomEntityNo(chatRoomId);
        return messageEntities.stream()
                .map(MessageEntity::toDto)
                .collect(Collectors.toList());
    }

    // 특정 채팅방의 최신 메시지 조회 메서드
    @Transactional
    public MessageDto findLatestMessageByChatRoomNo(Integer chatRoomNo) {
        // Optional 처리
        return messageRepository.findTopByChatRoomEntityNoOrderByCreateDateDesc(chatRoomNo)
                .map(MessageEntity::toDto)
                .orElse(null); //마지막 값 없는 경우 null 처리
    }

}
