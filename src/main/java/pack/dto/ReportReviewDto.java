package pack.dto;

import lombok.*;
import pack.entity.ReportEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportReviewDto {
    private Integer no;
    private int userNo;
    private Integer reviewNo;
    private int code;


    //toEntity
    public ReportEntity toEntity() {
        return ReportEntity.builder()
                .no(this.no)
                .userNo(this.userNo)
                .reviewNo(this.reviewNo)
                .date(LocalDateTime.now())
                .code(this.code)
                .build();
    }
}