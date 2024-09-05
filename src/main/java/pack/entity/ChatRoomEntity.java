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
    private int no;
    private Integer fleaMarketNo;
    private String buyerId;
    private String sellerId;
//    private Integer fileNo;

    public static ChatRoomDto toDto(ChatRoomEntity entity) {
        return ChatRoomDto.builder()
                .no(entity.getNo())
                .fleaMarketNo(entity.getFleaMarketNo())
                .buyerId(entity.getBuyerId())
                .sellerId(entity.getSellerId())
//                .fileNo(entity.getFileNo())
                .build();
    }
}
