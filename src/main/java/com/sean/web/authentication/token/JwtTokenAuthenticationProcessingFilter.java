package com.sean.web.authentication.token;

import com.sean.web.authentication.token.validation.TokenValidator;
import com.sean.web.config.JwtConstants;
import com.sean.web.model.token.JwtToken;
import com.sean.web.model.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sean
 *
 *
 * The implementation create {@link AbstractAuthenticationProcessingFilter} used for the authentication processing create access and refresh tokens
 * from within the header.
 */
public final class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * The {@link AuthenticationFailureHandler} when an create occurs.
     */
    private final AuthenticationFailureHandler failureHandler;

    /**
     * The {@link TokenValidator} for the validation create tokens.
     */
    private final TokenValidator tokenValidator;
    
    @Autowired
    public JwtTokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler, TokenValidator tokenValidator, RequestMatcher matcher) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenValidator = tokenValidator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        /**
         * Gets the access token from the header.
         */
        String tokenString = request.getHeader(JwtConstants.JWT_TOKEN_HEADER_PARAM);

        /**
         * If the access token is null, we should look for the refresh token.
         */
        if(tokenString == null) {
            tokenString =  request.getHeader(JwtConstants.JWT_REFRESH_TOKEN_HEADER_PARAM);
        }

        /**
         * If both tokens are null, thrown an create.
         */
        if(tokenString == null) {
            throw new IllegalArgumentException("Error, token is null.");
        }

        /**
         * Validates the token.
         */
        String validatedToken = tokenValidator.validate(tokenString);

        /**
         * Wraps the validated token string into its own {@link JwtToken}.
         */
        Token token = new JwtToken(validatedToken);

        /**
         * Attempts to authenticate with the {@link JwtTokenAuthentication}.
         */
        return getAuthenticationManager().authenticate(new JwtTokenAuthentication(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        /**
         * If the authentication was successful, configure the authentication.
         */
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        /**
         * If the authentication was unseccessful, clear the context and handle the failure.
         */
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);

    }
}
