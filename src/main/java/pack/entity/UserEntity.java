package pack.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Integer no;

    @Column(unique = true)
    private String id;

    private String pw;
    private String name;
    private Boolean signoutIs;
    private LocalDateTime signupDate;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FleamarketEntity> fleaMarkets;
    
    public static UserDto toDto(UserEntity entity) {
        return UserDto.builder()
                .no(entity.getNo())
                .id(entity.getId())
                .pw(entity.getPw())
                .name(entity.getName())
                .signoutIs(entity.getSignoutIs())
                .signupDate(entity.getSignupDate())
                .mNo(entity.getFleaMarkets() != null ?
                        entity.getFleaMarkets().stream()
                        .map(FleamarketEntity::getNo) // 필요한 데이터만 가져오자. 아니면 스택오버플로우가 생김
                                .collect(Collectors.toList()) : null)
                .build();
    }
    
}
