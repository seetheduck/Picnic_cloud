package pack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.FleamarketEntity;
import pack.entity.ReportEntity;
import pack.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDto {
    private int no;
    private Integer reviewNo;
    private Integer fleaMarketNo;
    private LocalDateTime date;
    private int code;
    private int userNo;

    //toEntity
    private static ReportEntity toEntity(ReportDto dto) {
        return ReportEntity.builder()
                .no(dto.getNo())
                .reviewNo(dto.getReviewNo())
                .fleaMarketNo(dto.getFleaMarketNo())
                .date(dto.getDate())
                .code(dto.getCode())
                .userNo(dto.getUserNo())
                .build();
    }
}
