package pack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.PlaceDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="place")
public class PlaceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가 설정
	private int no; 
	
	private String placeType;
    private String name;
    private String address;
    private Boolean likeIs;
    private Integer likeCnt;// 좋아요 
    private float point; //평점
    private String image;
    private String description;
    private String tel;
    private String entranceFee;
    private String operationTime;
    private int reviewCount; // 리뷰수 

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//   	private List<LikesEntity> likes;
    
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
    			.reviewCount(entity.getReviewCount())
//    			.likes(entity.getLikes() != null ?
//    				entity.getLikes().stream()
//    				.map(LikesEntity::toLikesPlaceDto)
//    				.collect(Collectors.toList()) : List.of())
    			.build();
    	
    }
}
