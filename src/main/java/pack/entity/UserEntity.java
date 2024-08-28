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
import pack.dto.UserDto;
import pack.dto.UserdetailDto;

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

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserdetailEntity userDetail;
    
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FleamarketEntity> fleaMarkets;

//    @OneToMany(mappedBy = "userEntity")
//    private List<FilesEntity> files;
    
 // DTO 변환 메서드
    public static UserDto toDto(UserEntity entity) {
     	return UserDto.builder()
                 .no(entity.getNo())
                 .id(entity.getId())
                 .pw(entity.getPw())
                 .name(entity.getName())
                 .signoutIs(entity.getSignoutIs())
                 .signUpDate(entity.getSignUpDate())
                 .mNo(entity.getFleaMarkets() != null ?
                         entity.getFleaMarkets().stream()
                         .map(FleamarketEntity::getMNo) // FleamarketEntity의 no를 가져옴
                         .collect(Collectors.toList()) : null)
                 .fNo(null) // 필요한 경우 수정
                  .build();
     }
    // UserDetail DTO 변환 메서드

    public UserdetailDto getUserDetailDto() {
        if (userDetail == null) {
            return null;
        }
        return UserdetailDto.builder()
                .address(userDetail.getAddress())
                .gender(userDetail.getGender())
                .email(userDetail.getEmail())
                .childAge(userDetail.getChildAge())
                .userStat(userDetail.getUserStat())
                .build();
    }   
}
