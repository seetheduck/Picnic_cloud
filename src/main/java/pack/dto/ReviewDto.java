package pack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.ReviewEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
	
    private Integer rNo;

    private String rId;
    private String rCont;
    private LocalDateTime rCreateDate;
    private Boolean rLike;
    private Integer rLikeCnt;
    private Boolean rDelIs;
    private LocalDateTime rDelDate;
    private Boolean rBlocked;
    private Integer rBlockedCnt;
    private String rIp;
    private int rPno; // db 변동사항에 따라 추가된 부분 

    //private List<LikesDto> likes;

    //private List<ReportDto> reports;
	
	//toEntity: dto > entity
    public static ReviewEntity toReviewEntity(ReviewDto dto) {
    	return ReviewEntity.builder()
    			.rNo(dto.getRNo())
    			.rId(dto.getRId())
    			.rCont(dto.getRCont())
    			.rCreateDate(dto.getRCreateDate())
    			.rLike(dto.getRLike())
    			.rLikeCnt(dto.getRLikeCnt())
    			.rDelIs(dto.getRDelIs())
    			.rDelDate(dto.getRDelDate())
    			.rBlocked(dto.getRBlocked())
    			.rBlockedCnt(dto.getRBlockedCnt())
    			.rIp(dto.getRIp())
    			.rPno(dto.getRPno())
    			//.likes(dto.getLikes() != null ? 
    			//	dto.getLikes().stream()
    			//		.map(LikesDto::toLikesEntity)
    			///		.collect(Collectors.toList()) : Collections.emptyList())
    			//.reports(dto.getReports() != null ?
    			//	dto.getReports().stream()
    			//		.map(ReportDto::toReportEntity)
    				//	.collect(Collectors.toList()) : Collections.emptyList())
    			.build();
    }
}
