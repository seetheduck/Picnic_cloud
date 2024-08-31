package pack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.ChatRoomDto;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomEntity {
    @Id
    private Integer no;
    private Integer fleaMarketNo;
    private String buyerId;
    private String sellerId;
    private Integer messageNo;
    private Integer fileNo;

    @OneToOne(mappedBy = "chatRoomEntity")
    private MessageEntity messageEntity;

    public static ChatRoomDto toDto(ChatRoomEntity entity) {
        return ChatRoomDto.builder()
                .no(entity.getNo())
                .fleaMarketNo(entity.getFleaMarketNo())
                .buyerId(entity.getBuyerId())
                .sellerId(entity.getSellerId())
                .messageNo(entity.getMessageNo())
                .fileNo(entity.getFileNo())
                .build();
    }
}
