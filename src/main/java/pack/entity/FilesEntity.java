package pack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.FilesDto;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilesEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fNo;
    private String fType;
    private String fPath;
    private LocalDateTime fUploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_id", referencedColumnName = "id")
    private UserEntity userEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="f_market_no")
    private FleamarketEntity fleamarketEntity;
    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="f_chat_no")
//    private ChatEntity chatEntity;
    
	// entity > dto 변환
	public static FilesDto toDto(FilesEntity entity) {
		return FilesDto.builder()
				.fNo(entity.getFNo())
				.fType(entity.getFType())
				.fPath(entity.getFPath())
				.fUploadDate(entity.getFUploadDate())
				.fUserId(entity.getUserEntity().getId()) //사용자 아이디
				.fMarketNo(FleamarketEntity.toDto(entity.fleamarketEntity).getMNo())
				.userDto(UserEntity.toDto(entity.userEntity))
				.build();
	}
}
