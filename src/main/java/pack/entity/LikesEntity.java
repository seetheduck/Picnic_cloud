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
import pack.dto.LikesDto;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesEntity {
	@Id
	private Integer no;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="userid")
	private UserEntity userEntity;
	
	private Integer placeNo;
	private Integer reviewNo;
	
	//플리마켓 좋아요
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="flea_market_no")
	private FleamarketEntity fleamarketEntity;
	
	//toDto
	public static LikesDto toDto(LikesEntity entity) {
		return LikesDto.builder()
				.no(entity.getNo())
				.userid(entity.getUserEntity().getId())
				.fleaMarketNo(entity.getFleamarketEntity().getNo())
				.build();
	}
}
