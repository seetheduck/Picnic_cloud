package pack.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.ChatRoomEntity;
import pack.entity.ChatRoomEntity.MessageType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {
	private int no;
    private int fleaMarketNo;
    private String buyerId;
    private String sellerId;
    private int messageNo;
    private int fileNo;

    public static ChatRoomEntity toEntity(ChatRoomDto chatRoomDto) {
        if (chatRoomDto == null) {
            return null;
        }

        return ChatRoomEntity.builder()
                .no(chatRoomDto.getNo())
                .fleaMarketNo(chatRoomDto.getFleaMarketNo())
                .buyerId(chatRoomDto.getBuyerId())
                .sellerId(chatRoomDto.getSellerId())
                .messageNo(chatRoomDto.getMessageNo())
                .fileNo(chatRoomDto.getFileNo())
                .build();
    }

}
