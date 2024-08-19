package pack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.ChatDto;

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
    private String cBuyerId;
    private String cSellerId;
    private Integer cMessageNo; //메시지 번호 (어플리케이션 단 증)
    private String cMessageCont;
    private Integer cFileNo;
    private LocalDateTime cCreateDate; //시간까지 저장되는 타입

    @Enumerated(EnumType.STRING)
    private MessageType cMessageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_flea_no")//플리 게시판 번호
    private FleamarketEntity fleamarketEntity;
    
//    @OneToMany(mappedBy = "chatEntity") //단방향
//    private List<FilesEntity> Files;

    public enum MessageType {
        text, file;
        
        public static MessageType fromString(String type) {
            for (MessageType messageType : MessageType.values()) {
                if (messageType.name().equalsIgnoreCase(type)) {
                    return messageType;
                }
            }
            throw new IllegalArgumentException("No enum constant for " + type);
        }
    }
        
        //entity > dto 변환
        public static ChatDto toDto(ChatEntity entity) {
        	//private FleamarketEntity FleamarketEntity;
        	return ChatDto.builder()
        			.cNo(entity.getCNo())
        			.cBuyerId(entity.getCBuyerId())
        			.cSellerId(entity.getCSellerId())
        			.cMessageNo(entity.getCMessageNo())
        			.cMessageCont(entity.getCMessageCont())
        			.cCreateDate(entity.getCCreateDate())
        			.fleamarketDto(FleamarketEntity.toDto(entity.fleamarketEntity)) //플리마켓
        			.cFleaNo(entity.getCFileNo())
//        			.fNo(entity.getFiles().stream().map(FilesEntity::getFNo).collect(Collectors.toList()))
        			.build();
        }
        
}
