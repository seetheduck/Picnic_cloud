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
    private String senderId;
    private Boolean readIs;
    private String messageContents;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_room_no")
    private ChatRoomEntity chatRoomEntity;

    public static MessageDto toDto(MessageEntity entity) {
        return MessageDto.builder()
                .no(entity.getNo())
//                .senderId(entity.getSenderId())
                .messageContents(entity.getMessageContents())
//                .createDate(entity.getCreateDate())
                .chatRoomNo(entity.getChatRoomEntity().getNo())
                .sellerId(entity.getChatRoomEntity().getSellerId()) //dto에는 sellerID를 가져온다.
                .build();
    }
}
