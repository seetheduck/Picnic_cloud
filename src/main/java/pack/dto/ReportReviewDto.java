package pack.dto;

import lombok.*;
import pack.entity.ReportEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportReviewDto {
    private Integer reviewNo;
    private int code;
    private int userNo;

    //toEntity
    public ReportEntity toEntity() {
        return ReportEntity.builder()
                .reviewNo(this.reviewNo)
                .code(this.code)
                .userNo(this.userNo)
                .build();
    }
}
