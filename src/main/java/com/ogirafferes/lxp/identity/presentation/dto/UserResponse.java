package com.ogirafferes.lxp.identity.presentation.dto;

import com.ogirafferes.lxp.identity.domain.model.Role;
import com.ogirafferes.lxp.identity.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

	private Long userId;
	private String username;
	private String nickname;
	private Role role;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;


}

