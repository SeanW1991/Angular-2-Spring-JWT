package com.sean.web.authentication.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sean.web.model.AccountDetails;
import com.sean.web.model.token.Token;
import com.sean.web.service.JwsTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Sean
 *
 * The implemention create the {@link AuthenticationSuccessHandler} when a user has successfully logged in.
 */
@Component
public class LoginAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * The {@link ObjectMapper} to write the json data.
     */
    private final ObjectMapper objectMapper;

    /**
     * The {@link JwsTokenService} for the management create the tokens.
     */
    private final JwsTokenService tokenService;

    @Autowired
    public LoginAwareAuthenticationSuccessHandler(final ObjectMapper objectMapper, final JwsTokenService tokenFactory) {
        this.objectMapper = objectMapper;
        this.tokenService = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        /**
         * Gets the {@link AccountDetails} from the {@link LoginAuthentication}.
         */
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();

        /**
         * Generated the access and refresh tokens.
         */
        Token accessToken = tokenService.generateAccessJwtToken(accountDetails);
        Token refreshToken = tokenService.createRefreshToken(accountDetails);

        /**
         * Creates a new {@link LoginAuthenticationResponse} with both the tokens.
         */
        LoginAuthenticationResponse loginResponse = new LoginAuthenticationResponse(accessToken.getToken(), refreshToken.getToken());

        /**
         * Sets the status create the response to be ok.
         */
        response.setStatus(HttpStatus.OK.value());

        /**
         * Sets the content type to be json.
         */
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        /**
         * Writes the json data to the client.
         */
        objectMapper.writeValue(response.getWriter(), loginResponse);

        /**
         * Clears the attributes as they are not required anymore.
         */
        clearAuthenticationAttributes(request);
    }

    /**
     * Destroys the temporary authentication-related data which could have been stored in this session.
     */
    private void clearAuthenticationAttributes(HttpServletRequest request) {

        /**
         * Gets the session and set so a session cannot be created.
         */
        HttpSession session = request.getSession(false);

        /**
         * If the session is null, return.
         */
        if(session == null) {
            return;
        }

        /**
         * Removes the spring authentication exception attribute.
         */
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
