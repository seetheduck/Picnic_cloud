package pack.entity;

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
import pack.dto.LikesPlaceDto;

@Entity
@Table(name="likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesPlaceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer no;
	
	private String userId;
	private Integer placeNo; //성능 최적화와 단순한 구현을 선호할 때 유용

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "flea_market_no")
//    private FleaMarketEntity fleaMarketEntity;
	
	//entity to dto
	public static LikesPlaceDto toLikesPlaceDto(LikesPlaceEntity entity) {
		return LikesPlaceDto.builder()
				.no(entity.getNo())
				.userId(entity.getUserId())
				.placeNo(entity.getPlaceNo())
				.build();
	}
}
