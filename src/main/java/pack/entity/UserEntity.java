package pack.entity;

import jakarta.persistence.*;
import lombok.*;
import pack.dto.FilesDto;
import pack.dto.FleamarketDto;
import pack.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FleamarketEntity> fleaMarkets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilesEntity> files;
    
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
                        .map(FleamarketEntity::getMNo)
                                //.map(FleamarketEntity::toDto)
                                .collect(Collectors.toList()) : null)
                .fNo(entity.getFiles() != null ?
                        entity.getFiles().stream()
                                .map(FilesEntity::getFNo)
                                .collect(Collectors.toList()) : null)
                .build();
    }
    
}
