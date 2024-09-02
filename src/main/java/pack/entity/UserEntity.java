package pack.entity;

import jakarta.persistence.*;
import lombok.*;
import pack.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity{

    @Id
    private Integer no;

    @Column(unique = true)
    private String id;

    private String pw;
    private String name;
    private Boolean accountDeleteIs;
    private LocalDateTime signupDate;
    
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FleamarketEntity> fleaMarkets;

 // DTO 변환 메서드
    public static UserDto toDto(UserEntity entity) {

     	return UserDto.builder()
                 .no(entity.getNo())
                 .id(entity.getId())
                 .pw(entity.getPw())
                 .name(entity.getName())
                 .accountDeleteIs(entity.getAccountDeleteIs())
                 .signupDate(entity.getSignupDate())
                 .mNo(entity.getFleaMarkets() != null ?
                         entity.getFleaMarkets().stream()
                         .map(FleamarketEntity::getNo) // FleamarketEntity의 no를 가져옴
                         .collect(Collectors.toList()) : null)
                 .fNo(null) // 필요한 경우 수정
                  .build();
     }

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
