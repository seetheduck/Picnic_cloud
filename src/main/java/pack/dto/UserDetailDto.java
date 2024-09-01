package pack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.UserDetailEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailDto {
	private int no;
    private String address;
    private boolean gender;
    private String email;
    private int childAge;
    private boolean userstat;
    private LocalDateTime accountDeleteDate;

    public UserDetailEntity toEntity() {
        return UserDetailEntity.builder()
                .no(this.no)
                .address(this.address)
                .gender(this.gender)
                .email(this.email)
                .childAge(this.childAge)
                .userstat(this.userstat)
                .accountDeleteDate(this.accountDeleteDate)
                .build();
    }
}
