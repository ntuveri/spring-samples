package com.inpectotpm.springwebtesting;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// Introduced to highlight differences between unit, integration and e2e tests in case of Spring Web objects
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.isEmpty(authorizationHeader) ||
			!authorizationHeader.startsWith("Basic ")) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}

		String username;
		String password;
		try {
			String authorizationCredentialsBase64 = authorizationHeader.replace("Basic ", "");
			String authorizationCredentials = new String(
				Base64Utils.decodeFromString(authorizationCredentialsBase64),
				StandardCharsets.UTF_8);

			username = authorizationCredentials.split(":")[0];
			password = authorizationCredentials.split(":")[1];
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}

		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ||
			!username.equals("admin") || !password.equals("password123")) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return false;
		}

		return true;
	}


}
