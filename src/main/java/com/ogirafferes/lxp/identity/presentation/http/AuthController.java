package com.ogirafferes.lxp.identity.presentation.http;

import com.ogirafferes.lxp.identity.application.UserService;
import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.identity.domain.model.UserRole;
import com.ogirafferes.lxp.identity.presentation.dto.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
// 커밋 수정확인용
	private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
	public String loginPage() {
		return "login";
	}



	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute RegisterRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		try {
			UserRole role = UserRole.valueOf(request.getRole().toUpperCase());
			userService.register(request.getUsername(), request.getPassword(), request.getNickname(), role);
			return "redirect:/auth/login?success=true";
		} catch (IllegalArgumentException e) {
			bindingResult.rejectValue("username", "error.username", e.getMessage());
			return "register";
		}
	}
}

