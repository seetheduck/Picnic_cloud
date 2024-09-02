package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.FleamarketEntity;
import pack.entity.LikesEntity;
import pack.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesDto {
	private int no;
	private String userId;
	private Integer reviewNo;
	private Integer fleaMarketNo;


	public static LikesEntity toEntity(LikesDto dto) {
		return LikesEntity.builder()
				.no(dto.getNo())
				.userId(dto.getUserId())
				.reviewNo(dto.getReviewNo())
				.build();
	}
}
