package pack.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
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

    //@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<LikesEntity> likes;

   // @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<ReportEntity> reports;
    
    //toDto: entity > dto
    public static ReviewDto toReviewDto(ReviewEntity entity) {
    	return ReviewDto.builder()
    			.rNo(entity.getRNo())
    			.rCont(entity.getRCont())
    			.rCreateDate(entity.getRCreateDate())
    			.rLike(entity.getRLike())
    			.rLikeCnt(entity.getRLikeCnt())
    			.rDelIs(entity.getRDelIs())
    			.rDelDate(entity.getRDelDate())
    			.rBlocked(entity.getRBlocked())
    			.rBlockedCnt(entity.getRBlockedCnt())
    			.rIp(entity.getRIp())
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