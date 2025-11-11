package com.ogirafferes.lxp.identity.presentation.dto;

import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterRequest {



	@NotBlank(message = "사용자명은 필수입니다.")
	@Size(min = 3, max = 20, message = "사용자명은 3자 이상 20자 이하여야 합니다.")
	private String username;

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Size(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
	private String password;

	@NotBlank(message = "닉네임은 필수입니다.")
	@Size(max = 100, message = "닉네임은 100자 이하여야 합니다.")
	private String nickname;

	private UserRole role = UserRole.valueOf("USER"); // 기본값은 USER

    public static RegisterRequest registerRequest(User user){
        return RegisterRequest.builder()
                .username(user.getUsername())
                .password(null)
                .nickname(user.getNickname())
                .role((user.getRole()))
                .build();
    }

}

