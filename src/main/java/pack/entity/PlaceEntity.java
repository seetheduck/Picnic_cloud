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
	private int no; //
	
	private String placeType;
    private String name;
    private String address;
    private Boolean likeIs;
    private Integer likeCnt;
    private float point;
    private String image;
    private String description;
    private String tel;
    private String entranceFee;
    private String operationTime;

    //@OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
   // private List<LikesEntity> likes;
    
  //toDto: entity > dto
    public static PlaceDto toPlaceDto(PlaceEntity entity) {
    	return PlaceDto.builder()
    			.no(entity.getNo())
    			.placeType(entity.getPlaceType())
    			.name(entity.getName())
    			.address(entity.getAddress())
    			.likeIs(entity.getLikeIs())
    			.likeCnt(entity.getLikeCnt())
    			.point(entity.getPoint())
    			.image(entity.getImage())
    			.description(entity.getDescription())
    			.tel(entity.getTel())
    			.entranceFee(entity.getEntranceFee())
    			.operationTime(entity.getOperationTime())
    			//.likes(entity.getLikes() != null ?
    			//	entity.getLikes().stream()
    			//	.map(LikesEntity::toLikesDto)
    			//	.collect(Collectors.toList()) : Collections.emptyList())
    			.build();
    	
    }
}
