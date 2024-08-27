package pack.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

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
	    private String userid;
	    private String type;
	    private String path;
	    private LocalDateTime uploadDate;
	    private Integer marketNo;
	    private Integer chatNo;
	
//	private UserDto userDto;

//	private MultipartFile multipart;

    //dto를 entity로 변환
    public static FilesEntity toEntity (FilesDto dto) {
    	if (dto == null) {
            return null;
        }
    	// DTO의 필드를 사용하여 엔티티를 생성
    	return FilesEntity.builder()
                .no(dto.getNo())
                .userid(dto.getUserid())
                .path(dto.getPath())
                .uploadDate(dto.getUploadDate())
//    			.userEntity(UserDto.toEntity(dto.getUserDto()))
    			.build();
    }
    
}
