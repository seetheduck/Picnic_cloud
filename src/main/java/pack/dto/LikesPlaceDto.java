package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pack.entity.LikesEntity;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesPlaceDto {
    private int no;
    private String userId;
    private Integer placeNo;
    
    private boolean liked; // dto 좋아요 유무
	private int likeCount;// 좋아요 수

    //toEntity
    public static LikesEntity toEntity(LikesPlaceDto dto) {
        if (dto == null) {
            return null;
        }
        return LikesEntity.builder()
                .no(dto.getNo())
                .userId(dto.getUserId())
                .placeNo(dto.getPlaceNo())
                .build();
    }
}
