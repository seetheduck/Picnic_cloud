package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.ChatRoomListEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomListDto {
    private int no;
    private String userId;
    private Integer chatRoomNo;

//    private List<ChatRoomDto> chatRooms;

    public static ChatRoomListEntity toEntity(ChatRoomListDto dto) {
        return ChatRoomListEntity.builder()
                .no(dto.getNo())
                .userId(dto.getUserId())
                .chatRoomNo(dto.getChatRoomNo())
                .build();
    }
}