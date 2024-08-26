package pack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
import pack.dto.ReportDto;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer no; 
    private Integer reviewNo;

	//플리마켓 신고
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="flea_market_no")
	private FleamarketEntity fleamarketEntity;
	
    private LocalDateTime date;
    private Integer code;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_no")
	private UserEntity userEntity;
	
	//toDto
	public static ReportDto toDto(ReportEntity entity) {
		return ReportDto.builder()
				.userNo(entity.getUserEntity().getNo())
				.fleaMarketNo(entity.getFleamarketEntity().getMNo())
				.build();
	}
	
}
