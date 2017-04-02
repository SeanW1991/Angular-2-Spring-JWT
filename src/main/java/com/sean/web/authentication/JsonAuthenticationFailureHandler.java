package com.sean.web.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sean.web.model.error.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sean
 *
 * The implementation create the {@link AuthenticationFailureHandler} that sends a json response
 * when an exception occurs when something goes wrong on authentication.
 */
@Component
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {

	/**
	 * The {@link ObjectMapper} for the json create message.
	 */
	private final ObjectMapper objectMapper;

    @Autowired
    public JsonAuthenticationFailureHandler(ObjectMapper mapper) {
        this.objectMapper = mapper;
    }	

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		objectMapper.writeValue(response.getWriter(), ErrorMessage.create(e.getMessage()));
	}
}
