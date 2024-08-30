package pack.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private Integer no;

    private String id;
    private String contents;
    private LocalDateTime createDate;
    private Boolean likeIs;
    private Integer likeCnt; //좋아요 
    private Boolean delIs;
    private LocalDateTime delDate;
    private Boolean blocked;
    private Integer blockedCnt; //신고
    private String ip;
    private int placeNo; 
    
    @Column(nullable = false) // 평점은 필수 입력으로 설정
    private float point; // 별점 필드 (평점)


    //@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<LikesEntity> likes;

   // @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<ReportEntity> reports;
    
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
    			//.likes(entity.getLikes() != null ?
    			//		entity.getLikes().stream()
    			//		.map(LikesEntity::toLikesDto)
    				//	.collect(Collectors.toList()) : Collections.emptyList())
    			//.reports(entity.getReports() != null ?
    			//		entity.getReports().stream()
    			//		.map(ReportEntity::toReportDto)
    			//		.collect(Collectors.toList()) : Collections.emptyList())
    			.build();
    }
}