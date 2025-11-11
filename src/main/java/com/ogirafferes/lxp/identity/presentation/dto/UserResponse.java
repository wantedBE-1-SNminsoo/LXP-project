package com.ogirafferes.lxp.identity.presentation.dto;

import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.model.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

	private Long userId;
	private String username;
	private String nickname;
	private UserRole role;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static UserResponse userResponse(User user) {
		return UserResponse.builder()
				.userId(user.getUserId())
				.username(user.getUsername())
				.nickname(user.getNickname())
				.role(user.getRole())
				.createdAt(user.getCreatedAt())
				.updatedAt(user.getUpdatedAt())
				.build();
	}
}

