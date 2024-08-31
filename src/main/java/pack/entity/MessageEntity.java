package pack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.MessageDto;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity {
    @Id
    private Integer no;
    private Integer chatRoomId;
    private Integer senderId;
    private String messageContents;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chatRoomId", referencedColumnName = "no", insertable = false, updatable = false)
    private ChatRoomEntity chatRoomEntity;

    public static MessageDto toDto(MessageEntity entity) {
        return MessageDto.builder()
                .no(entity.getNo())
                .chatRoomId(entity.getChatRoomId())
                .senderId(entity.getSenderId())
                .messageContents(entity.getMessageContents())
                .createDate(entity.getCreateDate())
                .build();
    }
}
