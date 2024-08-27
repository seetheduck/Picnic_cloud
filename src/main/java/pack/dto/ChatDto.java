package pack.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.ChatEntity;
import pack.entity.ChatEntity.MessageType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {
    private Integer cNo; //채팅방 번호

    private Integer cFleaNo; //플리 게시판 번호
    private String cBuyerId;
    private String cSellerId;
    private Integer cMessageNo; //메시지 번호 (어플리케이션 단 증가)
    private String cMessageCont;
    private LocalDateTime cCreateDate;

    private MessageType cMessageType;

    private List<Integer> fNo;
    
    private FleamarketDto fleamarketDto;
    
    public static ChatEntity toEntity(ChatDto dto) {
        if (dto == null) {
            return null;
        }
    // DTO의 필드를 사용하여 엔티티를 생성
    return ChatEntity.builder()
            .cNo(dto.getCNo())
            .cBuyerId(dto.getCBuyerId())
            .cSellerId(dto.getCSellerId())
            .cMessageNo(dto.getCMessageNo())
            .cMessageCont(dto.getCMessageCont())
            .cCreateDate(dto.getCCreateDate())
            .cMessageType(dto.getCMessageType())
//            .cFileNo(dto.getCFileNo())
            .fleamarketEntity(FleamarketDto.toEntity(dto.getFleamarketDto()))
            .build();
    }

}
