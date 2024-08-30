package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.LikesReviewEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesReviewDto {
	private Integer no;
	
	private String userId;
	private Integer placeNo;
	
	//dto to entity
	public static LikesReviewEntity toLikesReviewEntity(LikesReviewDto dto) {
		return LikesReviewEntity.builder()
				.no(dto.getNo())
				.userId(dto.getUserId())
				.placeNo(dto.getPlaceNo())
				.build();
	}
}
