package com.sean.web.authentication.login;

import com.sean.web.model.AccountDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * @author Sean
 * An implementation create {@link AbstractAuthenticationToken} used for the authentication create the login that
 * uses the {@link LoginCredentials} for the {@link AbstractAuthenticationToken#getCredentials()} and
 * the {@link AccountDetails} for the {@link AbstractAuthenticationToken#getPrincipal()}.
 */
public class LoginAuthentication extends AbstractAuthenticationToken {

    /**
     * The {@link LoginCredentials} that contains the username
     * and password create the user.
     */
    private final LoginCredentials loginCredentials;

    /**
     * The {@link AccountDetails} that contains the username and the authorities.
     */
    private final AccountDetails accountDetails;

    /**
     * Creates a new {@link LoginAuthentication}.
     * @param loginRequest The login details used.
     * @param accountDetails The account details containing the required information about the account.
     */
    public LoginAuthentication(LoginCredentials loginRequest, AccountDetails accountDetails) {
        super(Collections.emptyList());
        this.loginCredentials = loginRequest;
        this.accountDetails = accountDetails;
    }

    @Override
    public Object getCredentials() {
        return loginCredentials;
    }

    @Override
    public Object getPrincipal() {
        return accountDetails;
    }
}
