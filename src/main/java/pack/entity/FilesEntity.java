package pack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private Integer no;
	
//    private String userid;
    private String path;
    private LocalDateTime uploadDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="flea_market_no", referencedColumnName = "no")
    private FleamarketEntity fleamarketEntity;
    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="f_chat_no")
//    private ChatEntity chatEntity;
    
	// entity > dto 변환
    public static FilesDto toDto(FilesEntity entity) {
        return FilesDto.builder()
                .no(entity.getNo())
                .userid(entity.getUserEntity() != null ? entity.getUserEntity().getId() : null)
                .path(entity.getPath())
                .uploadDate(entity.getUploadDate())
                .marketNo(entity.getFleamarketEntity().getNo())
                .build();
    }
}
