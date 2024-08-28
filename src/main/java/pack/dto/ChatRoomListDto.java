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
	private int no;
    private int userId;
    private int chatRoomId;

    public static ChatRoomListEntity toEntity(ChatRoomListDto chatRoomListDto) {
        if (chatRoomListDto == null) {
            return null;
        }

        return ChatRoomListEntity.builder()
                .no(chatRoomListDto.getNo())
                .userId(chatRoomListDto.getUserId())
                .chatRoomId(chatRoomListDto.getChatRoomId())
                .build();
    }
}
