package com.sean.web.authentication.token;

import com.sean.web.model.AccountDetails;
import com.sean.web.model.token.Token;
import com.sean.web.service.JwsTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

 /**
  * The implemention of the {@link AuthenticationProvider} used for the authentication of the token.
  * and transforms it into a {@link AccountDetails}.
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

   private final JwsTokenService tokenService;

   @Autowired
    public JwtAuthenticationProvider(JwsTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        /**
         * Gets the token from the authentication credentials.
         */
        Token accessToken = (Token) authentication.getPrincipal();

        /**
         * The token used to authenticate.
         */
        String token = accessToken.getToken();

        /**
         * Transforms a token into user credentials.
         */
        AccountDetails accountDetails = tokenService.jwsClaimsToUserContext(token);

        /**
         * A new {@link JwtTokenAuthentication).
         */
        return new JwtAccountAuthentication(accountDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtTokenAuthentication.class.isAssignableFrom(authentication));
    }
}
