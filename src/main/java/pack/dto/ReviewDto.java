package pack.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
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
	@Id
    private Integer no;

    private String id;
    private String contents;
    private LocalDateTime createDate;
    private Boolean likeIs;
    private Integer likeCnt;
    private Boolean delIs;
    private LocalDateTime delDate;
    private Boolean blocked;
    private Integer blockedCnt;
    private String ip;
    private int placeNo; 
    private float point;

    //private List<LikesReviewDto> likes; // 좋아요 정보 추가

    //private List<ReportDto> reports;
	
	//toEntity: dto > entity
    public static ReviewEntity toReviewEntity(ReviewDto dto) {
    	return ReviewEntity.builder()
    			.no(dto.getNo())
    			.id(dto.getId())
    			.contents(dto.getContents())
    			.createDate(dto.getCreateDate())
    			.likeIs(dto.getLikeIs())
    			.likeCnt(dto.getLikeCnt())
    			.delIs(dto.getDelIs())
    			.delDate(dto.getDelDate())
    			.blocked(dto.getBlocked())
    			.blockedCnt(dto.getBlockedCnt())
    			.ip(dto.getIp())
    			.placeNo(dto.getPlaceNo())
    			.point(dto.getPoint())
//    			.likes(dto.getLikes() != null ? 
//    				dto.getLikes().stream()
//    					.map(LikesReviewDto::toLikesReviewEntity)
//    					.collect(Collectors.toList()) : Collections.emptyList())
    			//.reports(dto.getReports() != null ?
    			//	dto.getReports().stream()
    			//		.map(ReportDto::toReportEntity)
    				//	.collect(Collectors.toList()) : Collections.emptyList())
    			.build();
    }
}
