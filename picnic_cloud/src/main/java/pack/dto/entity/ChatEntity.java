package pack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import pack.dto.ChatDto;
import pack.entity.FleamarketEntity;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cNo; //채팅방 번호

    private Integer cFleaNo; //플리 게시판 번호
    private String cBuyerId;
    private String cSellerId;
    private Integer cMessageNo; //메시지 번호 (어플리케이션 단 증)
    private String cMessageCont;
    private LocalDateTime cCreateDate;

    @Enumerated(EnumType.STRING)
    private MessageType cMessageType;

    private Integer cFileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_flea_no", insertable = false, updatable = false)
    private FleamarketEntity fleamarketEntity;

    public enum MessageType {
        TEXT, FILE
    }
        
        //entity > dto 변환
        public static ChatDto toDto(ChatEntity entity) {
        	//private FleamarketEntity FleamarketEntity;
        	return ChatDto.builder()
        			.cNo(entity.getCNo())
        			.cFleaNo(entity.getCFleaNo())
        			.cBuyerId(entity.getCBuyerId())
        			.cSellerId(entity.getCSellerId())
        			.cMessageNo(entity.getCMessageNo())
        			.cMessageCont(entity.getCMessageCont())
        			.cCreateDate(entity.getCCreateDate())
        			.fleamarketDto(FleamarketEntity.toDto(entity.fleamarketEntity)) //플리마켓
        			.build();
        }
        
}
