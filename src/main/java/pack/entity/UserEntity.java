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
@Table(name = "user")
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
    private Boolean gender;
    private String email;
    private String address;
    private Integer childAge;
    private Boolean userStat;
    private Boolean signoutIs;
    private LocalDateTime signUpDate;

    @Column(unique = true)
    private Integer kakaoNo;

    private String kakaoEmail;
    private String profileImageUrl;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime tokenExpiration;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FleamarketEntity> fleaMarkets;

//    @OneToMany(mappedBy = "userEntity")
//    private List<FilesEntity> files;
    
    public static UserDto toDto(UserEntity entity) {
        return UserDto.builder()
                .no(entity.getNo())
                .id(entity.getId())
                .pw(entity.getPw())
                .name(entity.getName())
                .gender(entity.getGender())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .childAge(entity.getChildAge())
                .userStat(entity.getUserStat())
                .signoutIs(entity.getSignoutIs())
                .signUpDate(entity.getSignUpDate())
                .kakaoNo(entity.getKakaoNo())
                .kakaoEmail(entity.getKakaoEmail())
                .profileImageUrl(entity.getProfileImageUrl())
                .accessToken(entity.getAccessToken())
                .refreshToken(entity.getRefreshToken())
                .tokenExpiration(entity.getTokenExpiration())
                .mNo(entity.getFleaMarkets() != null ?
                        entity.getFleaMarkets().stream()
                        .map(FleamarketEntity::getMNo) // 필요한 데이터만 가져오자. 아니면 스택오버플로우가 생김
                                .collect(Collectors.toList()) : null)
                .build();
    }
    
}
