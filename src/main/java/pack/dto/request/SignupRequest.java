package pack.dto.request;

import lombok.Data;
import pack.dto.UserDetailDto;
import pack.dto.UserDto;

@Data
public class SignupRequest {
	private UserDto userDto;
    private UserDetailDto userDetailDto;
}
