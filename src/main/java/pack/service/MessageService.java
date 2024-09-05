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
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Transactional
    public MessageDto saveMessage(MessageDto messageDto) {
        System.out.println(messageDto);
        // 메시지가 속한 채팅방을 조회
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(messageDto.getChatRoomNo())
                .orElseThrow(() -> new RuntimeException("Chat room not found"));


        MessageEntity messageEntity = MessageDto.toEntity(messageDto,chatRoomEntity);

        //채팅방 번호
        Integer maxMassegeNo = messageRepository.findMaxMessageNo();
        if(maxMassegeNo == null) {
            maxMassegeNo = 1;
        }
        messageEntity.setNo(maxMassegeNo + 1);

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
}
