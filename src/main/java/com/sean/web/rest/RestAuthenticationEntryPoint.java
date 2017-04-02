package com.sean.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * The message when trying to access a page when the user is unauthorised.
	 */
	private static final String UNAUTHORIZED_ACCESS_MESSAGE = "Unauthorized access.";

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)  throws IOException, ServletException {
		response.sendError(HttpStatus.UNAUTHORIZED.value(), UNAUTHORIZED_ACCESS_MESSAGE);
	}
}
