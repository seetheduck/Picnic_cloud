package pack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.FleamarketEntity;
import pack.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FleamarketDto {

	 private Integer no;
	    private String userid; 
	    private String title;
	    private Integer price;
	    private String contents; 
	    private LocalDateTime createdate;
	    private LocalDateTime updatedate; 
	    private Boolean favorite;
	    private Integer favoriteCnt;
	    private String category;
	    private Boolean blocked;
	    private Integer blockedCnt;

	    private String filePath;
    
    //페이징 처리
	private int totalPages, currentPage;
	private Long totalElements;

    // 채팅번호
//    private List<Integer> cNo; 

	  //dto를 entity로 변환
	    public static FleamarketEntity toEntity(FleamarketDto dto) {
	        if (dto == null) {
	            return null;
	        }
	        
	        return FleamarketEntity.builder()
	                .no(dto.getNo())
	                .title(dto.getTitle())
	                .price(dto.getPrice())
	                .contents(dto.getContents())
	                .createdate(dto.getCreatedate())
	                .updatedate(dto.getUpdatedate())
	                .favorite(dto.getFavorite())
	                .favoriteCnt(dto.getFavoriteCnt())
	                .category(dto.getCategory())
	                .blocked(dto.getBlocked())
	                .blockedCnt(dto.getBlockedCnt())
	                .userEntity(dto.getUserid() != null ? UserEntity.builder().id(dto.getUserid()).build() : null)
	                .build();
	        
	    }
}
