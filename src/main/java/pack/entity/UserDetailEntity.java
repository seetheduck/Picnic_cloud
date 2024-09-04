package pack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.UserDetailDto;

@Entity
@Table(name = "user_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailEntity {
	@Id
    private int no;
    
    private String address;
    private boolean gender;
    private String email;
    private int childAge;
    private boolean userstat;
    private LocalDateTime accountDeleteDate;

    public UserDetailDto toDto() {
        return UserDetailDto.builder()
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
