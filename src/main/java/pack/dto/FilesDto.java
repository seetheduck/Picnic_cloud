package pack.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.FilesEntity;
import pack.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilesDto {
	@Id
	private Integer fNo;

	private String fUserId;
	private String fType; // 파일 타입
	private String fPath; // 파일 경로
	private LocalDateTime fUploadDate;
	private UserDto userDto;


    //dto를 entity로 변환
    public static FilesEntity toEntity (FilesDto dto) {
    	if (dto == null) {
            return null;
        }
    	// DTO의 필드를 사용하여 엔티티를 생성
    	return FilesEntity.builder()
    			.fNo(dto.getFNo())
    			.fUserId(dto.getFUserId())
    			.fType(dto.getFType())
    			.fPath(dto.getFPath())
    			.fUploadDate(dto.getFUploadDate())
    			.build();
    }
}
