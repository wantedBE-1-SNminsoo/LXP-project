package com.ogirafferes.lxp.identity.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "users1")
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


    public User(String username, String nickname, String encode) {
    }

    @PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}


    public static User register(String username, String nickname, String rawPassword, PasswordEncoder encoder, UserRole role) {
        return User.builder().
                username(username).
                passwordHash(encoder.encode(rawPassword)).
                nickname(nickname).
                role(role).
                build();
    }

    private void validate(String username, String password) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("username is required");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("password is required");
    }

	@Builder
	private User(String username, String passwordHash, String nickname, UserRole role) {
		validate(username, passwordHash);
        this.username = username;
		this.passwordHash = passwordHash;
		this.nickname = nickname;
		this.role = role;
		this.deletedFlag = false;
	}


}

