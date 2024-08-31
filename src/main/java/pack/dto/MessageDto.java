package pack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.MessageEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private Integer no;
    private Integer chatRoomId;
    private Integer senderId;
    private String messageContents;
    private LocalDateTime createDate;

    public static MessageEntity toEntity(MessageDto dto) {
        return MessageEntity.builder()
                .no(dto.getNo())
                .chatRoomId(dto.getChatRoomId())
                .senderId(dto.getSenderId())
                .messageContents(dto.getMessageContents())
                .createDate(dto.getCreateDate())
                .build();
    }
}