package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.ChatRoomEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {
    private Integer no;
    private Integer fleaMarketNo;
    private String buyerId;
    private String sellerId;
    private Integer messageNo;
    private Integer fileNo;

    public static ChatRoomEntity toEntity(ChatRoomDto dto) {
        return ChatRoomEntity.builder()
                .no(dto.getNo())
                .fleaMarketNo(dto.getFleaMarketNo())
                .buyerId(dto.getBuyerId())
                .sellerId(dto.getSellerId())
                .messageNo(dto.getMessageNo())
                .fileNo(dto.getFileNo())
                .build();
    }
}
