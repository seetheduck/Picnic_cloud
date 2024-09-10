package pack.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.ChatRoomDto;
import pack.dto.MessageDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardAndMessagesDto {
    private ChatRoomDto chatRoom; // 채팅방 정보
    private List<MessageDto> messages; // 채팅방에 속한 메시지들

    // 필요한 경우, 추가적인 정보 필드를 포함할 수 있습니다
    // 예: 게시판 내용, 게시글 작성자 정보 등
}