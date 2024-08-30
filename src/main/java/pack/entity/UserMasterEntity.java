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
import pack.dto.UserMasterDto;

@Entity
@Table(name = "user_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMasterEntity {
	@Id
	private int no;
    private String id;
    private String pw;
    private String name;
    private boolean signoutIs;
    private LocalDateTime signupDate;

    public UserMasterDto toDto() {
        return UserMasterDto.builder()
                .no(this.no)
                .id(this.id)
                .pw(this.pw)
                .name(this.name)
                .signoutIs(this.signoutIs)
                .signupDate(this.signupDate)
                .build();
    }

}
