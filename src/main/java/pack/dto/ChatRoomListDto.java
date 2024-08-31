package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.ChatRoomListEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomListDto {
    private Integer no;
    private Integer userId;
    private Integer chatRoomId;

    public static ChatRoomListEntity toEntity(ChatRoomListDto dto) {
        return ChatRoomListEntity.builder()
                .no(dto.getNo())
                .userId(dto.getUserId())
                .chatRoomId(dto.getChatRoomId())
                .build();
    }
}