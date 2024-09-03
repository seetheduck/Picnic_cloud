package pack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import pack.dto.LikesDto;
import pack.dto.LikesPlaceDto;
import pack.dto.LikesReviewDto;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesEntity {
	@Id
	private int no;
	private String userId;
	private Integer reviewNo;
	private Integer fleaMarketNo;
	private Integer placeNo;

	//toDto
	public static LikesDto toDto(LikesEntity entity) {
		return LikesDto.builder()
				.no(entity.getNo())
				.userId(entity.getUserId())
				.fleaMarketNo(entity.getFleaMarketNo())
				.build();
	}

	// to LikesReviewDto
	public static LikesReviewDto toLikesReviewDto(LikesEntity entity) {
		if (entity == null) {
			return null;
		}
		return LikesReviewDto.builder()
				.no(entity.getNo())
				.userId(entity.getUserId())
				.reviewNo(entity.getReviewNo())
				.build();


	}

	// to LikesPlaceDto
	public static LikesPlaceDto toLikesPlaceDto(LikesEntity entity) {
		if (entity == null) {
			return null;
		}
		return LikesPlaceDto.builder()
				.no(entity.getNo())
				.userId(entity.getUserId())
				.placeNo(entity.getPlaceNo())
				.build();


	}

}
