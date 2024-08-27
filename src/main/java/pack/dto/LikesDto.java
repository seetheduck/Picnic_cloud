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
	private Integer no;
	
	private String userid;
	private Integer placeNo;
	private Integer reviewNo;
	private Integer fleaMarketNo;
	
	private UserDto user;
	private FleamarketDto fleaMarket;
	
	
	public static LikesEntity toEntity(LikesDto dto) {
		return LikesEntity.builder()
				.no(dto.getNo())
				.userEntity(UserEntity.builder().id(dto.getUserid()).build())
				.fleamarketEntity(FleamarketEntity.builder().no(dto.getFleaMarketNo()).build())
				.build();
	}
}
