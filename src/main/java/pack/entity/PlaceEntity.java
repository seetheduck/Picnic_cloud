package pack.entity;

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
import pack.dto.PlaceDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="place")
public class PlaceEntity {
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

    //@OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
   // private List<LikesEntity> likes;
    
  //toDto: entity > dto
    public static PlaceDto toPlaceDto(PlaceEntity entity) {
    	return PlaceDto.builder()
    			.pNo(entity.getPNo())
    			.pCategory(entity.getPCategory())
    			.pName(entity.getPName())
    			.pAddress(entity.getPAddress())
    			.pLike(entity.getPLike())
    			.pLikeCnt(entity.getPLikeCnt())
    			.pPoint(entity.getPPoint())
    			.pImage(entity.getPImage())
    			.pExplain(entity.getPExplain())
    			.pTel(entity.getPTel())
    			.pPay(entity.getPPay())
    			.pDay(entity.getPDay())
    			//.likes(entity.getLikes() != null ?
    			//	entity.getLikes().stream()
    			//	.map(LikesEntity::toLikesDto)
    			//	.collect(Collectors.toList()) : Collections.emptyList())
    			.build();
    	
    }
}
