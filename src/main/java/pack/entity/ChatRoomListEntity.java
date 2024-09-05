package pack.entity;

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
import pack.dto.ChatRoomListDto;

@Entity
@Table(name = "chat_room_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomListEntity {
    @Id
    private int no;
    private String userId;
    private int chatRoomNo;

    public static ChatRoomListDto toDto(ChatRoomListEntity entity) {
        return ChatRoomListDto.builder()
                .no(entity.getNo())
                .userId(entity.getUserId())
                .chatRoomNo(entity.getChatRoomNo())
                .build();
    }
}