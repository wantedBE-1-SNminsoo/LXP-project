package com.ogirafferes.lxp.identity.application;

import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.model.UserRole;
import com.ogirafferes.lxp.identity.domain.repository.UserRepository;
import com.ogirafferes.lxp.identity.presentation.dto.RegisterRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
	public User register(RegisterRequest dto) {
		if (userRepository.existsByUsername(dto.getUsername())) {
			throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
		}


		User user = User.register(dto, passwordEncoder);
		return userRepository.save(user);
	}


}

