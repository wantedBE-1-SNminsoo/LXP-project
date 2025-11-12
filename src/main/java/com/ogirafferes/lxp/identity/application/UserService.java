package com.ogirafferes.lxp.identity.application;

import com.ogirafferes.lxp.identity.domain.model.Role;
import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.repository.RoleRepository;
import com.ogirafferes.lxp.identity.domain.repository.UserRepository;
import com.ogirafferes.lxp.identity.presentation.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Transactional
	public User register(RegisterRequest dto) {
		if (userRepository.existsByUsername(dto.getUsername())) {
			throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
		}



        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

		User user = User.register(dto, passwordEncoder, role);
		return userRepository.save(user);
	}


}

