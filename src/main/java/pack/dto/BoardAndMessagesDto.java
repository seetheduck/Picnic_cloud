// BoardAndMessagesDto.java
package pack.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardAndMessagesDto {
    private Integer no; //플리마켓 고유번호
    private String title; //플리마켓 제목
    private Integer price; //플리마켓 가격
    private List<MessageDto> messages;
}
