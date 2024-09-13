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
    private String userId;


    //toEntity
    public ReportEntity toEntity(int userNo) {
        return ReportEntity.builder()
                .no(this.no)
                .userNo(userNo)
                .reviewNo(this.reviewNo)
                .date(LocalDateTime.now())
                .code(this.code)
                .build();
    }
}