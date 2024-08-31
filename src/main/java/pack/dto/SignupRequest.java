package pack.dto;

import lombok.Data;

@Data
public class SignupRequest {
	private UserDto userDto;
    private UserDetailDto userDetailDto;
}
