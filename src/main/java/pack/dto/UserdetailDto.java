package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.UserEntity;
import pack.entity.UserdetailEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserdetailDto {

    private String address;
    private Boolean gender;
    private String email;
    private Integer childAge;
    private Boolean userStat;

    // UserDetailDto를 UserDetail로 변환하는 메서드
    public static UserdetailEntity toEntity(UserdetailDto dto, UserEntity userEntity) {
        if (dto == null) {
            return null;
        }

        return UserdetailEntity.builder()
                .userEntity(userEntity) // userEntity와 연관짓기
                .address(dto.getAddress())
                .gender(dto.getGender())
                .email(dto.getEmail())
                .childAge(dto.getChildAge())
                .userStat(dto.getUserStat())
                .build();
    }
}
