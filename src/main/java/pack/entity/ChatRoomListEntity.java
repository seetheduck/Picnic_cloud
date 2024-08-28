package pack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_room_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomListEntity {
	private int no;
    private int userId;
    private int chatRoomId;

    public ChatRoomListDto toDto() {
        return ChatRoomListDto.builder()
                .no(this.no)
                .userId(this.userId)
                .chatRoomId(this.chatRoomId)
                .build();
    }
}
