package pack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.UserMasterEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMasterDto {
	 private int no;
	    private String id;
	    private String pw;
	    private String name;
	    private boolean signoutIs;
	    private LocalDateTime signupDate;

	    public UserMasterEntity toEntity() {
	        return UserMasterEntity.builder()
	                .no(this.no)
	                .id(this.id)
	                .pw(this.pw)
	                .name(this.name)
	                .signoutIs(this.signoutIs)
	                .signupDate(this.signupDate != null ? this.signupDate : LocalDateTime.now())
	                .build();
	    }
}
