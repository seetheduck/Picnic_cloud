package pack.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.FleamarketDto;

@Entity
@Table(name = "flea_market")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FleamarketEntity {
	 	@Id
	 	private Integer mNo;
	    private String mTitle;
	    private Integer mPrice;
	    private String mCont;
	    private LocalDateTime mCreateDate;
	    private LocalDateTime mUpdateDate;
	    private Boolean mLike; //좋아요 유무
	    private Integer mLikeCnt; //좋아요 수
	    private String mCategory; //게시물 카테고리
	    private Boolean mBlocked; //관리자) 블락처리 유무
	    private Integer mBlockedCnt; //관리자) 블락처리 수

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "m_id", referencedColumnName = "id") //글쓴이
	    private UserEntity userEntity;

	    @OneToOne(mappedBy = "fleamarketEntity") //하나의 게시판에는 이미지 1개만 
	    private FilesEntity files;
	    
	    @OneToMany(mappedBy = "fleamarketEntity")
	    private List<ChatEntity> chats;

//	    @OneToMany(mappedBy = "FleamarketEntity")
//	    private List<Likes> likes;
	    
//	    @OneToMany(mappedBy = "FleamarketEntity")
//	    private List<Report> reports;
	     

		// entity > dto 변환
		public static FleamarketDto toDto(FleamarketEntity entity) {

			 return FleamarketDto.builder()
		                .mNo(entity.getMNo())
		                .mTitle(entity.getMTitle())
		                .mPrice(entity.getMPrice())
		                .mCont(entity.getMCont())
		                .mCreateDate(entity.getMCreateDate())
		                .mUpdateDate(entity.getMUpdateDate())
		                .mLike(entity.getMLike())
		                .mLikeCnt(entity.getMLikeCnt())
		                .mCategory(entity.getMCategory())
		                .mBlocked(entity.getMBlocked())
		                .mBlockedCnt(entity.getMBlockedCnt())
		                .mId(entity.getUserEntity().getId())  // UserEntity를 UserDto로 변환
		                .mFilePath(entity.getFiles() != null
		                	? entity.getFiles().getFPath(): null) //파일의 경로 가져가기
		                .cNo(entity.getChats() != null ?
		                        entity.getChats().stream()
		                        .map(ChatEntity::getCNo) //엔티티클래스 ::해당 엔티티(pk) 
		                                //.map(ChatEntity::toDto) //전부 가져오면 오류 생긴다.
		                                .collect(Collectors.toList()) : null)  //ChatDto "리스트"로 변환
		                .build();
		}
	    
}
