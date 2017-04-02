package com.sean.web.rest;

import com.sean.web.authentication.token.validation.TokenValidator;
import com.sean.web.config.JwtConstants;
import com.sean.web.model.AccountDetails;
import com.sean.web.model.token.Token;
import com.sean.web.service.JwsTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
/**
 * @author Sean
 *
 * A dedicated {@link RestController} used to update a access token when it has expired.
 * The refresh token is sent on the request to this page and a new token is generated.
 */
public class TokenUpdateController {

    @Autowired
    private JwsTokenService tokenService;

    @Autowired
    private TokenValidator tokenValidator;

    @RequestMapping(value="/api/token/refresh")
    public @ResponseBody Token refresh(HttpServletRequest request, HttpServletResponse response) {

        /**
         * Validates the token and retrieves the token from the header.
         */
        String tokenPayload = tokenValidator.validate(request.getHeader(JwtConstants.JWT_REFRESH_TOKEN_HEADER_PARAM));

        /**
         * Gets the {@link AccountDetails} from the principal.
         */
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof AccountDetails)) {
            throw new IllegalArgumentException("Error, user login principal not usercontext");
        }

        AccountDetails accountDetails = (AccountDetails) principal;

        /**
         * Generates a new access token.
         */
        Token newToken = tokenService.generateAccessJwtToken(accountDetails);

        /**
         * Returns the access token.
         */
        return newToken;
    }

}
