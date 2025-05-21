package com.example.demo.config;


import com.example.demo.config.auth.exceptionHandler.CustomAccessDeniedHandler;
import com.example.demo.config.auth.exceptionHandler.CustomAuthenticationEntryPoint;
import com.example.demo.config.auth.jwt.JwtAuthorizationFilter;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import com.example.demo.config.auth.loginHandler.CustomLoginFailureHandler;
import com.example.demo.config.auth.loginHandler.CustomLoginSuccessHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutSuccessHandler;
import com.example.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler;
	@Autowired
	private CustomLogoutHandler customLogoutHandler;
	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private JwtAuthorizationFilter jwtAuthorizationFilter;


	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		//CSRF비활성화
		http.csrf((config)->{config.disable();});
		//CSRF토큰 쿠키형태로 전달
//		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		//권한체크
		http.authorizeHttpRequests((auth)->{
			auth.requestMatchers("/","/join","/login").permitAll();
			auth.requestMatchers("/user").hasRole("USER");
			auth.requestMatchers("/manager").hasRole("MANAGER");
			auth.requestMatchers("/admin").hasRole("ADMIN");
			auth.anyRequest().authenticated();
		});

		//로그인
		http.formLogin((login)->{
			login.permitAll();
			login.loginPage("/login");
			login.successHandler(customLoginSuccessHandler);
			login.failureHandler(new CustomLoginFailureHandler());
		});
		//로그아웃
		http.logout((logout)->{
			logout.permitAll();
			logout.addLogoutHandler(customLogoutHandler);
			logout.logoutSuccessHandler(customLogoutSuccessHandler);
		});
		//예외처리

		http.exceptionHandling((ex)->{
			ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
			ex.accessDeniedHandler(new CustomAccessDeniedHandler());
		});

		//OAUTH2-CLIENT
		http.oauth2Login((oauth2)->{
			oauth2.loginPage("/login");
		});

		//SESSION INVALIDATED
		http.sessionManagement((sessionManagerConfigure)->{
			sessionManagerConfigure.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		});


		//JWT FILTER ADD 로그인 이후에 인증하기 위한코드
		http.addFilterBefore(jwtAuthorizationFilter, LogoutFilter.class);

		return http.build();


	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
