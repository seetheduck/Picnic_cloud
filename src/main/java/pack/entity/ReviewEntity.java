package pack.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.ReviewDto;

@Entity
@Table(name="review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    private String id;
    private String contents;
    private LocalDateTime createDate;
    private Boolean likeIs; //좋아요 토글
    private Integer likeCnt; //좋아요 수
    private Boolean delIs;
    private LocalDateTime delDate;
    private Boolean blocked;
    private Integer blockedCnt; //신고 수
    private String ip;
    private int placeNo; 
    
    @Column(nullable = false) // 평점은 필수 입력으로 설정
    private float point; // 별점 필드 (평점)


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikesEntity> likes;  // 좋아요 연관 관계

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEntity> reports; //신고 연관 관계
    
    //toDto: entity > dto
    public static ReviewDto toReviewDto(ReviewEntity entity) {
    	return ReviewDto.builder()
    			.no(entity.getNo())
    			.id(entity.getId())
    			.contents(entity.getContents())
    			.createDate(entity.getCreateDate())
    			.likeIs(entity.getLikeIs())
    			.likeCnt(entity.getLikeCnt())
    			.delIs(entity.getDelIs())
    			.delDate(entity.getDelDate())
    			.blocked(entity.getBlocked())
    			.blockedCnt(entity.getBlockedCnt())
    			.ip(entity.getIp())
    			.placeNo(entity.getPlaceNo())
				.likes(entity.getLikes() != null ?
						entity.getLikes().stream()
								.map(LikesEntity::toLikesReviewDto)  // LikesEntity를 LikesReviewDto로 변환
								.collect(Collectors.toList()) : List.of())  // 좋아요 목록을 DTO 리스트로 변환
    			.reports(entity.getReports() != null ?
    					entity.getReports().stream()
    					.map(ReportEntity::toReportReviewDto)
    					.collect(Collectors.toList()) : List.of())
    			.build();
    }
}