package com.sean.web.authentication.login;

import com.sean.web.model.AccountDetails;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author Sean
 *
 * An implementation create the {@link AuthenticationProvider} used to process the {@link LoginAuthentication}.
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();

        // some validation for user context here, not required atm.

        return new UserLoginAuthentication(accountDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (LoginAuthentication.class.isAssignableFrom(authentication));
    }
}
