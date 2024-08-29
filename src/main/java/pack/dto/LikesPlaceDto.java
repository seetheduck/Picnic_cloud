package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.LikesPlaceEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesPlaceDto {
	private Integer no;
	
	private String userId;
	private Integer placeNo;
	//private int likeCount; // db에는없는칼럼. 
	//클라이언트에게 데이터를 전송하기 위해 선언.
	
	// toEntity: dto > entity
	public static LikesPlaceEntity toLikesPlaceEntity(LikesPlaceDto dto) {
		return LikesPlaceEntity.builder()
				.no(dto.getNo())
				.userId(dto.getUserId())
				.placeNo(dto.getPlaceNo())
				.build();
				
	}
}
