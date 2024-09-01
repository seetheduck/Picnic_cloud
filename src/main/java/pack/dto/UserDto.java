package pack.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto{

    @Id
    private Integer no;

    @Column(unique = true)
    private String id;

    private String pw;
    private String name;
    private Boolean accountDeleteIs;
    private LocalDateTime signupDate;
    
    private List<Integer> mNo; //마켓 번호
    private List<Integer> fNo; //파일 번호
    
 // UserDto를 UserEntity로 변환하는 메서드
    public static UserEntity toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        UserEntity userEntity = UserEntity.builder()
                .no(dto.getNo())
                .id(dto.getId())
                .pw(dto.getPw())
                .name(dto.getName())
                .accountDeleteIs(dto.getAccountDeleteIs())
                .signupDate(dto.getSignupDate())
                .build();
        return userEntity;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .no(this.no)
                .id(this.id)
                .pw(this.pw)
                .name(this.name)
                .accountDeleteIs(this.accountDeleteIs)
                .signupDate(this.signupDate != null ? this.signupDate : LocalDateTime.now())
                .build();
    }
    
}
