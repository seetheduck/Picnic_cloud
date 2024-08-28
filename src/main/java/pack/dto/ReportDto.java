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
	private Integer no; 
//    private Integer reviewNo;
    private Integer fleaMarketNo;
    private LocalDateTime date;
    private Integer code;
    private Integer userNo;
    
    private UserDto user;
    private FleamarketDto fleamarket;
    
    //toEntity
    private static ReportEntity toEntity(ReportDto dto) {
    	return ReportEntity.builder()
    			.no(dto.getNo())
    			.fleamarketEntity(FleamarketEntity.builder().no(dto.getFleaMarketNo()).build())
    			.date(dto.getDate())
    			.code(dto.getCode())
    			.userEntity(UserEntity.builder().no(dto.getUserNo()).build())
    			.build();
    }
}
