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
	private int userNo;
	private Integer reviewNo;
	private Integer fleaMarketNo;
	private LocalDateTime date;
	private int code;

	//toDto
	public static ReportDto toDto(ReportEntity entity) {
		return ReportDto.builder()
				.no(entity.getNo())
				.userNo(entity.getUserNo())
				.reviewNo(entity.getReviewNo())
				.fleaMarketNo(entity.getFleaMarketNo())
				.date(LocalDateTime.now()) // 현재 시간으로 설정
				.code(entity.getCode())
				.build();
	}

	//toDto
	public static ReportReviewDto toReportReviewDto(ReportEntity entity) {
		return ReportReviewDto.builder()
				.reviewNo(entity.getReviewNo())
				.code(entity.getCode())
				.userNo(entity.getUserNo())
				.build();
	}





}
