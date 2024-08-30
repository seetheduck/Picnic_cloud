package pack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.LikesReviewDto;

@Entity
@Table(name="likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesReviewEntity {
	@Id
	private Integer no;
	
	private String userId;
	private Integer placeNo;
	
	//entity toDto
	public static LikesReviewDto toLikesReviewDto(LikesReviewEntity entity) {
		return LikesReviewDto.builder()
				.no(entity.getNo())
				.userId(entity.getUserId())
				.placeNo(entity.getPlaceNo())
				.build();
	}
	
	
}
