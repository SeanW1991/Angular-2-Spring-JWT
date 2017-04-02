package com.sean.web.authentication.login;

import com.sean.web.model.AccountDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Created by sean on 01/04/2017.
 */
public class UserLoginAuthentication extends AbstractAuthenticationToken {

    /**
     * The {@link AccountDetails} create the logged in user.
     */
    private final AccountDetails accountDetails;

    /**
     * Creates a new {@link UserLoginAuthentication} containing the {@link AccountDetails}.
     * @param accountDetails The details create the logged in account.
     */
    public UserLoginAuthentication(AccountDetails accountDetails) {
        super(accountDetails.getAuthorities());
        this.accountDetails = accountDetails;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
       return null;
    }

    @Override
    public Object getPrincipal() {
        return accountDetails;
    }
}
