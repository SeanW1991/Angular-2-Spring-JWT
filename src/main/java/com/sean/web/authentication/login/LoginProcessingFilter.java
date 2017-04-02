package com.sean.web.authentication.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sean.web.model.AccountDetails;
import com.sean.web.model.User;
import com.sean.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Sean
 *
 * The implementation of the {@link AbstractAuthenticationProcessingFilter} for the processing
 * of the login request.
 */
public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter {


    /**
     * The error message if the username or password is invalid.
     */
    private static final String USERNAME_PASSWORD_INVALID_ERROR = "Username or Password is invalid.";

	 /**
     * The error message If the username is not present.
     */
    private static final String USERNAME_NOT_PRESENT_ERROR = "Username is not present.";

	 /**
     * The error message if password is not present.
     */
    private static final String PASSWORD_NOT_PRESENT_ERROR = "Password is not present.";

	 /**
     * The error message if the username and password both are not present.
     */
    private static final String USERNAME_PASSWORD_NOT_PRESENT_ERROR = "Username or Password is not present.";

	 /**
     * The error message if an invalid authentication technique is used.
     */
    private static final String INVALID_AUTHENTICATION_TECHNIQUE = "Authentication technique invalid.";

	 /**
     * The dedicated {@link Logger} for this class.
     */
    private static Logger logger = LoggerFactory.getLogger(LoginProcessingFilter.class);

    private final UserService userService;

    private final AuthenticationSuccessHandler successHandler;

    private final AuthenticationFailureHandler failureHandler;

    private final ObjectMapper objectMapper;

    public LoginProcessingFilter(String defaultProcessUrl, ObjectMapper objectMapper, UserService userService, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(defaultProcessUrl);
        this.userService = userService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        /**
         * Reads the login request and decodes it the json request to a {@link LoginCredentials}.
         */
        LoginCredentials loginRequest = objectMapper.readValue(request.getReader(), LoginCredentials.class);

        String requestedUsername = loginRequest.getUsername();
        String requestedPassword = loginRequest.getPassword();

        boolean usernameValidation = StringUtils.isBlank(requestedUsername);
        boolean passwordValidation = StringUtils.isBlank(requestedPassword);

		 /**
         * Validates the login request credentials.
         */
        if(usernameValidation && passwordValidation) {
            throw new AuthenticationServiceException(USERNAME_PASSWORD_NOT_PRESENT_ERROR);
        } else if (usernameValidation) {
            throw new AuthenticationServiceException(USERNAME_NOT_PRESENT_ERROR );
        } else if (passwordValidation) {
            throw new AuthenticationServiceException(PASSWORD_NOT_PRESENT_ERROR);
        }

        Optional<User> userOptional = userService.findByUsername(requestedUsername);

        User user = userOptional.orElseThrow(() -> new BadCredentialsException(USERNAME_PASSWORD_INVALID_ERROR));

        if(!user.getPassword().equals(requestedPassword)) {
            throw new BadCredentialsException(USERNAME_PASSWORD_INVALID_ERROR);
        }

        if (user.getRoles() == null) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().name()))
                .collect(Collectors.toList());


        LoginAuthentication auth = new LoginAuthentication(loginRequest, new AccountDetails(requestedUsername, authorities));

        return this.getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
