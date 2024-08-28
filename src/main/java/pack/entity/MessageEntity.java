package pack.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.MessageDto;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity {
	private int no;
    private int chatRoomId;
    private int senderId;
    private String messageContents;
    private Date createDate;

    public MessageDto toDto() {
        return MessageDto.builder()
                .no(this.no)
                .chatRoomId(this.chatRoomId)
                .senderId(this.senderId)
                .messageContents(this.messageContents)
                .createDate(this.createDate)
                .build();
    }
}
