package com.example.demo.config.auth.loginHandler;

import java.io.IOException;


import com.example.demo.config.auth.jwt.JwtProperties;
import com.example.demo.config.auth.jwt.JwtTokenProvider;
import com.example.demo.config.auth.jwt.TokenInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

		log.info("CustomLoginSuccessHandler's onAuthenticationSuccess invoke..");

//		String username = authentication.getName();
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
		Cookie cookie = new Cookie(JwtProperties.ACCESS_TOKEN_COOKIE_NAME, tokenInfo.getAccessToken());
		cookie.setMaxAge(JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME);
		cookie.setPath("/");
		response.addCookie(cookie);

		response.sendRedirect(request.getContextPath()+"/");

	}

}
