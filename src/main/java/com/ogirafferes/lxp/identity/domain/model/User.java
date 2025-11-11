package com.ogirafferes.lxp.identity.domain.model;

import com.ogirafferes.lxp.identity.presentation.dto.RegisterRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(nullable = false, length = 100)
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private UserRole role;

	@Column(name = "deleted_flag", nullable = false)
	@ColumnDefault("false")
	private Boolean deletedFlag = false;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;


    @PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}


    public static User register(RegisterRequest dto, PasswordEncoder encoder) {
        return User.builder().
                username(dto.getUsername()).
                passwordHash(encoder.encode(dto.getPassword())).
                nickname(dto.getNickname()).
                role(dto.getRole()).
                build();
    }

    private void validate(String username, String password,String nickname,UserRole role) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("username is required");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("password is required");
        if (nickname == null || nickname.isBlank())
            throw new IllegalArgumentException("nickname is required");
        if (role == null)
            throw new IllegalArgumentException("role is required");
    }

	@Builder
	private User(String username, String passwordHash, String nickname, UserRole role) {
		validate(username, passwordHash, nickname, role);
        this.username = username;
		this.passwordHash = passwordHash;
		this.nickname = nickname;
		this.role = role;
		this.deletedFlag = false;
	}


}

