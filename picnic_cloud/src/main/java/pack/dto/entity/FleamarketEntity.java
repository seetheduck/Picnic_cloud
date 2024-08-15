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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.FleamarketDto;
import pack.dto.UserDto;

@Entity
@Table(name = "flea_market")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FleamarketEntity {
	 	@Id
	 	@GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer mNo;

	    @Column(unique = true)
	    private String mId;

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
	    private Integer mFileNo; //파일 번호

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "m_id", referencedColumnName = "id", insertable = false, updatable = false)
	    private UserEntity userEntity;

//	    @OneToMany(mappedBy = "FleamarketEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//	    private List<Likes> likes;

	    @OneToMany(mappedBy = "FleamarketEntity", cascade = CascadeType.ALL, orphanRemoval = false)
	    private List<ChatEntity> chats;
	    //orphanRemoval : 부모 엔티티에서 자식 엔티티를 제거할때 자신 엔티티를 자동으로 삭제하도록 설정
	    //기본 값이 false이며 이 경우 자식 엔티티가 더 이상 부모와 연관이 없다고 해도, 자식 엔티티를 수동으로 삭제해야한다.
	    

//	    @OneToMany(mappedBy = "FleamarketEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//	    private List<Report> reports;
	    

		// entity > dto 변환
		public static FleamarketDto toDto(FleamarketEntity entity) {
			// 직접 가져오면 한번에 전체 정보를 가져올 수 있지만 dto 크기가 커져 전송 효율이 낮아질 수 있다.
			// 채팅방의 전체 정보가 자주 필요할까? 고민 ..
//	    	List<Integer> cNoList =  new ArrayList<Integer>();
//	    	for(ChatEntity chatEtt : FleamarketEntity.getList())

			 return FleamarketDto.builder()
		                .mNo(entity.getMNo())
		                .mId(entity.getMId())
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
		                .mFileNo(entity.getMFileNo())
		                .userDto(UserEntity.toDto(entity.getUserEntity()))  // UserEntity를 UserDto로 변환
		                .chats(entity.getChats() != null ?
		                        entity.getChats().stream()
		                                .map(ChatEntity::toDto)
		                                .collect(Collectors.toList()) : null)  // ChatEntity 리스트를 ChatDto 리스트로 변환
		                .build();
		}
	    
}
