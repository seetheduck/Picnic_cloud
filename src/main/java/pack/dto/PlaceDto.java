package pack.dto;

import lombok.*;
import pack.entity.PlaceEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceDto {

	private int no; 
	
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
    private int reviewCount;

    //toEntity: dto > entity
    public static PlaceEntity toPlaceEntity(PlaceDto dto) {
    	return PlaceEntity.builder()
    			.no(dto.getNo())
    			.placeType(dto.getPlaceType())
    			.name(dto.getName())
    			.address(dto.getAddress())
    			.likeIs(dto.getLikeIs())
    			.likeCnt(dto.getLikeCnt())
    			.point(dto.getPoint())
    			.image(dto.getImage())
    			.description(dto.getDescription())
    			.tel(dto.getTel())
    			.entranceFee(dto.getEntranceFee())
    			.operationTime(dto.getOperationTime())
    			.reviewCount(dto.getReviewCount())

    			.build();
    			
    }
}
