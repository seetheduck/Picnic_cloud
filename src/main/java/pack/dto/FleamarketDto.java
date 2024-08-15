package pack.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.User;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.ChatEntity;
import pack.entity.FleamarketEntity;
import pack.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FleamarketDto {

	@Id
	private Integer mNo;

	@Column(unique = true)
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
	private Integer mFileNo; // 파일 번호

	private UserDto userDto; // 전체 사용자
	private List<ChatDto> chats; // 채팅
	private List<Integer> cNo;


	  //dto를 entity로 변환
	    public static FleamarketEntity toEntity(FleamarketDto dto) {
	        if (dto == null) {
	            return null;
	        }
	        // DTO의 필드를 사용하여 엔티티를 생성
	        return FleamarketEntity.builder()
	                .mNo(dto.getMNo())
	                .mId(dto.getMId())
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
	                .mFileNo(dto.getMFileNo())
	                .userEntity(UserDto.toEntity(dto.getUserDto()))
	                .chats(dto.getChats().stream()
                            .map(ChatDto::toEntity)
                            .collect(Collectors.toList()))
	                .build();
	    }
	    
}
