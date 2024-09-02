package pack.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<LikesReviewDto> likes; // 좋아요 정보 추가

    private List<ReportReviewDto> reports; // 신고 정보 추가
	
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
				.likes(dto.getLikes() != null ?
						dto.getLikes().stream()
								.map(LikesReviewDto::toEntity)  // LikesReviewDto를 LikesEntity로 변환
								.collect(Collectors.toList()) : List.of())  // 좋아요 DTO 리스트를 Entity 리스트로 변환
    			.reports(dto.getReports() != null ?
    				dto.getReports().stream()
    					.map(ReportReviewDto::toEntity)
    					.collect(Collectors.toList()) : List.of())
    			.build();
    }
}
