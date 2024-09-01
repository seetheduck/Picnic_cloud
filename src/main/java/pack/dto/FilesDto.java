package pack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.FilesEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilesDto {
	private Integer no;
	private String userId;
	private String type;
	private String path;
	private LocalDateTime uploadDate;
	private Integer marketNo;

	// dto를 entity로 변환
	 public static FilesEntity toEntity(FilesDto dto) {
	        if (dto == null) {
	            return null;
	        }
	        // DTO의 필드를 사용하여 엔티티를 생성
	        return FilesEntity.builder()
	                .no(dto.getNo())
					.userId(dto.getUserId())
	                .path(dto.getPath())
	                .uploadDate(LocalDateTime.now())
	                .build();
	    }

}
