package pack.dto;

import lombok.Data;

@Data
public class SignupRequest {
	private UserMasterDto userMasterDto;
    private UserDetailDto userDetailDto;
}
