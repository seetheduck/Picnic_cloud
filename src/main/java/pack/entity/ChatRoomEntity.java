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
import pack.dto.ChatRoomDto;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int no;
    private int fleaMarketNo;
    private String buyerId;
    private String sellerId;
    private int messageNo;
    private int fileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_flea_no")//플리 게시판 번호
    private FleamarketEntity fleamarketEntity;
        
        //entity > dto 변환
    public ChatRoomDto toDto() {
        return ChatRoomDto.builder()
                .no(this.no)
                .fleaMarketNo(this.fleaMarketNo)
                .buyerId(this.buyerId)
                .sellerId(this.sellerId)
                .messageNo(this.messageNo)
                .fileNo(this.fileNo)
                .build();
    }
        
}
