package com.ogirafferes.lxp.identity;

import com.ogirafferes.lxp.identity.application.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthService authService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/**", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
			.formLogin(form -> form
				.loginPage("/auth/login")
				.loginProcessingUrl("/auth/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/auth/login?error=true")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/auth/login")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
			)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.maximumSessions(1)
				.maxSessionsPreventsLogin(false)
			)
			.userDetailsService(authService);

		return http.build();
	}
}
//커밋수정용

