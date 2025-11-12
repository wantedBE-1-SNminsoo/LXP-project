package com.ogirafferes.lxp.identity.presentation.http;

import com.ogirafferes.lxp.identity.application.UserService;
import com.ogirafferes.lxp.identity.domain.model.Role;
import com.ogirafferes.lxp.identity.domain.repository.RoleRepository;
import com.ogirafferes.lxp.identity.presentation.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final UserService userService;
    private final RoleRepository roleRepository;


    @GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        model.addAttribute("roles", roleRepository.findAll());
        return "register";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute RegisterRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		try {
			userService.register(request);
			return "redirect:/auth/login?success=true";
		} catch (IllegalArgumentException e) {
			bindingResult.rejectValue("username", "error.username", e.getMessage());
			return "register";
		}
	}
}

