package pack.entity;

import jakarta.persistence.*;
import lombok.*;
import pack.dto.FilesDto;

import java.time.LocalDateTime;

import org.apache.catalina.User;

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

    private String fUserId;
    private String fType;
    private String fPath;
    private LocalDateTime fUploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity userEntity;

	// entity > dto 변환
	public static FilesDto toDto(FilesEntity entity) {
		return FilesDto.builder()
				.fNo(entity.getFNo())
				.fUserId(entity.getFUserId())
				.fType(entity.getFType())
				.fPath(entity.getFPath())
				.fUploadDate(entity.getFUploadDate())
				.userDto(UserEntity.toDto(entity.userEntity))
				.build();
	}
}
