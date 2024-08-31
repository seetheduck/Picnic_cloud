package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.MessageDto;
import pack.entity.MessageEntity;
import pack.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public MessageDto saveMessage(MessageDto messageDto) {
        MessageEntity messageEntity = MessageDto.toEntity(messageDto);
        MessageEntity savedEntity = messageRepository.save(messageEntity);
        return MessageEntity.toDto(savedEntity);
    }
}
