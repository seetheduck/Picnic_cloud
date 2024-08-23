package pack.dto;

import java.time.LocalDateTime;
import java.util.List;

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

	private Integer mNo;
	private String mId;
	private String mTitle;
	private Integer mPrice;
	private String mCont;
	private LocalDateTime mCreateDate;
	private LocalDateTime mUpdateDate;
	private Boolean mLike; // 좋아요 유무
	private Integer mLikeCnt; // 좋아요 수
	private String mCategory; // 게시물 카테고리
	private Boolean mBlocked; // 관리자) 블락처리 유무
	private Integer mBlockedCnt; // 관리자) 블락처리 수
	
	//첨부파일 path
    private String mFilePath;
    
    //페이징 처리
	private int totalPages, currentPage;
	private Long totalElements;

    // 채팅번호
    private List<Integer> cNo; 

	  //dto를 entity로 변환
	    public static FleamarketEntity toEntity(FleamarketDto dto) {
	        if (dto == null) {
	            return null;
	        }
	        
	        return FleamarketEntity.builder()
	                .mNo(dto.getMNo())
	                .mTitle(dto.getMTitle())
	                .mPrice(dto.getMPrice())
	                .mCont(dto.getMCont())
	                .mCreateDate(dto.getMCreateDate())
	                .mUpdateDate(dto.getMUpdateDate())
	                .mLike(dto.getMLike())
	                .mLikeCnt(dto.getMLikeCnt())
	                .mCategory(dto.getMCategory())
	                .mBlocked(dto.getMBlocked())
	                .mBlockedCnt(dto.getMBlockedCnt())
	                .userEntity(dto.getMId() != null ? UserEntity.builder().id(dto.getMId()).build() : null)
	                .build();
	        
	    }
}
