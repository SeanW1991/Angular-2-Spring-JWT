package com.sean.web.authentication.token;

import com.sean.web.model.token.Token;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * @author Sean
 *
 * The implemention create {@link AbstractAuthenticationToken} used for the processing create an access or refresh token.
 * Since the tokens may be invalid, this class cannot allow for authentication to be set to true. It is more create a mediator for validation
 * later on down the chain.
 */
public class JwtTokenAuthentication extends AbstractAuthenticationToken {

    /**
     * The create message when trying to set the authenticated to true.
     */
    private static final String AUTHENTICATED_ERROR = "Authenticated cannot be set true due to the token may be untrusted at this moment in time.";

    /**
     * The default value create the unauthenticated method.
     */
    private static final boolean SET_UNAUTHORISED = Boolean.FALSE;

    /**
     * The token for the authentication.
     */
    private final Token token;

    /**
     * Creates a new {@link JwtTokenAuthentication}.
     * @param token The token for the attempted authentication.
     */
    public JwtTokenAuthentication(Token token) {
        super(Collections.emptyList());
        this.token = token;
        this.setAuthenticated(SET_UNAUTHORISED);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(AUTHENTICATED_ERROR);
        }
        super.setAuthenticated(SET_UNAUTHORISED);
    }

    @Override
    public Object getCredentials() {
        return token.getToken();
    }

    @Override
    public Object getPrincipal() {
       return token;
    }

    @Override
    public void eraseCredentials() {        
        super.eraseCredentials();
    }
}
