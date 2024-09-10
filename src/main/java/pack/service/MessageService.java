package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.BoardAndMessagesDto;
import pack.dto.FleamarketDto;
import pack.dto.MessageDto;
import pack.entity.ChatRoomEntity;
import pack.entity.MessageEntity;
import pack.repository.ChatRoomRepository;
import pack.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private FleamarketService fleamarketService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Transactional
    public MessageDto saveMessage(MessageDto messageDto) {
        // 메시지가 속한 채팅방을 조회
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(messageDto.getChatRoomNo())
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        // 현재 시간을 설정
        messageDto.setCreateDate(LocalDateTime.now());

        // 메시지 엔티티 생성 및 저장
        MessageEntity messageEntity = MessageDto.toEntity(messageDto, chatRoomEntity);
        Integer maxMessageNo = messageRepository.findMaxMessageNo();
        messageEntity.setNo(maxMessageNo != null ? maxMessageNo + 1 : 1);

        // 메시지 저장
        MessageEntity savedEntity = messageRepository.save(messageEntity);

        // 저장된 메시지를 WebSocket을 통해 브로드캐스트
        MessageDto savedMessageDto = MessageEntity.toDto(savedEntity);
        messagingTemplate.convertAndSend("/topic/chatRoom/" + messageDto.getChatRoomNo(), savedMessageDto);

        return savedMessageDto;
    }

    // 특정 채팅방의 메시지와 게시판 정보 조회 메서드
    public BoardAndMessagesDto getMessagesAndBoardByChatRoomId(Integer chatRoomNo) {

        // 채팅방 정보 조회
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomNo)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        // 채팅방에 연결된 플리마켓 번호 조회
        Integer fleaMarketNo = chatRoomEntity.getFleaMarketNo(); // ChatRoomEntity에 fleaMarketNo가 있다고 가정

        // 플리마켓 번호로 게시판 정보 조회
        FleamarketDto boardDto = fleamarketService.getOne(fleaMarketNo);

        // 메시지 목록 조회
        List<MessageEntity> messageEntities = messageRepository.findByChatRoomEntityNo(chatRoomNo);
        List<MessageDto> messages = messageEntities.stream()
                .map(MessageEntity::toDto)
                .collect(Collectors.toList());

        // BoardAndMessagesDto 생성 및 반환
        return BoardAndMessagesDto.builder()
                .no(boardDto.getNo())
                .title(boardDto.getTitle())
                .price(boardDto.getPrice())
                .messages(messages)
                .build();
    }

    // 특정 채팅방의 최신 메시지 조회 메서드 (MessageList용)
    @Transactional
    public MessageDto findLatestMessageByChatRoomNo(Integer chatRoomNo) {

        // Optional 처리
        return messageRepository.findTopByChatRoomEntityNoOrderByCreateDateDesc(chatRoomNo)
                .map(MessageEntity::toDto)
                .orElse(null); //마지막 값 없는 경우 null 처리
    }

}
