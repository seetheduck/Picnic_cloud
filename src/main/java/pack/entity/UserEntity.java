package pack.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.UserDetailDto;
import pack.dto.UserDto;

@Entity
@Table(name = "user_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity{

    @Id
    private int no;

    @Column(unique = true)
    private String id;

    private String pw;
    private String name;
    private Boolean accountDeleteIs;
    private LocalDateTime signupDate;

    // DTO 변환 메서드

    public UserDto toDto() {
        return UserDto.builder()
                .no(this.no)
                .id(this.id)
                .pw(this.pw)
                .name(this.name)
                .accountDeleteIs(this.accountDeleteIs)
                .signupDate(this.signupDate)
                .build();
    }

}
