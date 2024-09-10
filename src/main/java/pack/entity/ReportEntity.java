package pack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.ReportDto;
import pack.dto.ReportReviewDto;

@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportEntity {
	@Id
	private int no;
	private int userNo; //신고하는 유저번호
	private Integer reviewNo;
	private Integer fleaMarketNo;
	private LocalDateTime date;
	private int code; //신고사유

	//toDto
	public static ReportDto toDto(ReportEntity entity) {
		return ReportDto.builder()
				.no(entity.getNo())
				.userNo(entity.getUserNo())
				.reviewNo(entity.getReviewNo())
				.fleaMarketNo(entity.getFleaMarketNo())
				.date(entity.getDate()) // 현재 시간으로 설정
				.code(entity.getCode())
				.build();
	}

	//toDto
	public static ReportReviewDto toReportReviewDto(ReportEntity entity) {
		return ReportReviewDto.builder()
				.no(entity.getNo())
				.userNo(entity.getUserNo())
				.reviewNo(entity.getReviewNo())
				.code(entity.getCode())
				.build();
	}
}
