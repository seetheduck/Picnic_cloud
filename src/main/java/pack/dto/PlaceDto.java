package pack.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.PlaceEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceDto {
	@Id
	private int pNo;
	
	private String pCategory;
    private String pName;
    private String pAddress;
    private Boolean pLike;
    private Integer pLikeCnt;
    private float pPoint;
    private String pImage;
    private String pExplain;
    private String pTel;
    private String pPay;
    private String pDay;
    
   // private List<LikesDto> likes;
    
    //toEntity: dto > entity
    public static PlaceEntity toPlaceEntity(PlaceDto dto) {
    	return PlaceEntity.builder()
    			.pNo(dto.getPNo())
    			.pCategory(dto.getPCategory())
    			.pName(dto.getPName())
    			.pAddress(dto.getPAddress())
    			.pLike(dto.getPLike())
    			.pLikeCnt(dto.getPLikeCnt())
    			.pPoint(dto.getPPoint())
    			.pImage(dto.getPImage())
    			.pExplain(dto.getPExplain())
    			.pTel(dto.getPTel())
    			.pPay(dto.getPPay())
    			.pDay(dto.getPDay())
    			//.likes(dto.getLikes() != null ? 
    			//	dto.getLikes().stream()
    			//		.map(LikesDto::toLikesEntity)
    			//		.collect(Collectors.toList()) : Collections.emptyList())
    			.build();
    			
    }
}
