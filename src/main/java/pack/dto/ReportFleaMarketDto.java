package pack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportFleaMarketDto {
	private Integer no;
	private Integer fleaMarketNo;
	private LocalDateTime date;
	private Integer code;
	private Integer userNo;
}
