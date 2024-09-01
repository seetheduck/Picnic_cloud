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
    private String userId;
    private String path;
    private LocalDateTime uploadDate;
    
    @ManyToOne
    @JoinColumn(name="flea_market_no", referencedColumnName = "no")
    private FleamarketEntity fleamarketEntity;
    
	// entity > dto 변환
    public static FilesDto toDto(FilesEntity entity) {
        return FilesDto.builder()
                .no(entity.getNo())
                .userId(entity.getUserId())
                .path(entity.getPath())
                .uploadDate(entity.getUploadDate())
                .marketNo(entity.getFleamarketEntity().getNo())
                .build();
    }
}
