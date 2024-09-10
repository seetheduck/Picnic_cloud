package pack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.ChatRoomEntity;
import pack.entity.MessageEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private Integer no;
    private String senderId;
    private Boolean readIs;
    private String messageContents;
    private LocalDateTime createDate;

    private String sellerId;
    private Integer chatRoomNo; //채팅방 번호

    public static MessageEntity toEntity(MessageDto dto, ChatRoomEntity chatRoomEntity) {

        return MessageEntity.builder()
                .no(dto.getNo())
                .senderId(dto.getSenderId())
                .messageContents(dto.getMessageContents())
                .createDate(dto.getCreateDate() != null ? dto.getCreateDate() : LocalDateTime.now())
                .chatRoomEntity(chatRoomEntity)
                .build();
    }
}