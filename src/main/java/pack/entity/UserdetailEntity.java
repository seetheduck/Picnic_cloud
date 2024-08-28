package pack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserdetailEntity {

	   @Id
	    @OneToOne
	    @JoinColumn(name = "user_no")
	    private UserEntity userEntity; // UserEntity와의 관계 설정

	    @Column(name = "address", length = 20)
	    private String address;

	    @Column(name = "gender")
	    private Boolean gender;

	    @Column(name = "email", length = 20)
	    private String email;

	    @Column(name = "child_age")
	    private Integer childAge;

	    @Column(name = "userstat")
	    private Boolean userStat;
}
