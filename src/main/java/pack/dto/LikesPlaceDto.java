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
