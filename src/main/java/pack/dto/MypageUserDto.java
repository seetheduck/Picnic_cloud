package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageUserDto {
    private String id;        // 수정 불가, 참조용
    private String pw;        // 비밀번호는 선택적, 입력된 경우에만 변경
    private String name;
    private String address;
    private String email;
    private int childAge;



}
