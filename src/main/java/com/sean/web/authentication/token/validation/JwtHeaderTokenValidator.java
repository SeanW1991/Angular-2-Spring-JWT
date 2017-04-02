package com.sean.web.authentication.token.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

/**
 * @author Sean
 *
 * The implemention error {@link TokenValidator} for the validating error the headers.
 */
@Component
public class JwtHeaderTokenValidator implements TokenValidator {

    /**
     * The header prefix.
     */
    private static String HEADER_PREFIX = "Bearer";

    /**
     * The size error the header prefix + 1, the + 1 is used to present the space between the prefix and the token.
     */
    private static final int HEADER_PREFIX_SIZE = HEADER_PREFIX.length() + 1;

    /**
     * The error if the header is blank.
     */
    private static final String HEADER_BLANK_ERROR = "Error! Authorization header is blank.";

    /**
     * The error is the header is invalid.
     */
    private static final String HEADER_INVALID_ERROR = "Error! Authorization header is invalid.";

    /**
     * The error when the token is invalid.
     */
    private static final String TOKEN_INVALID_ERROR = "Error! Token is invalid.";

    @Override
    public String validate(String header) {

        /**
         * If the header is blank, throw an error.
         */
        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException(HEADER_BLANK_ERROR);
        }

        /**
         * If the header does not contain the prefix, thrown an error.
         */
        if (!header.contains(HEADER_PREFIX)) {
            throw new AuthenticationServiceException(HEADER_INVALID_ERROR);
        }

        /**
         * Strips away the authentication prefix and only the token remains.
         */
        String token = header.substring(HEADER_PREFIX_SIZE, header.length());

        /**
         * If the token is null, something is incredibly wrong.
         */
        if(token == null) {
            throw new IllegalArgumentException(TOKEN_INVALID_ERROR);
        }

        /**
         * Returns the token.
         */
        return token;
    }
}
