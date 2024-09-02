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
public class LikesReviewDto {
    private int no;
    private String userId;
    private Integer reviewNo;

    //toEntity
    public static LikesEntity toEntity(LikesReviewDto dto) {
        if (dto == null) {
            return null;
        }
        return LikesEntity.builder()
                .no(dto.getNo())
                .userId(dto.getUserId())
                .reviewNo(dto.getReviewNo())
                .build();
    }

}
