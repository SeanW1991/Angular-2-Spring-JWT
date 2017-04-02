package com.sean.web.exceptions;

import com.sean.web.model.token.Token;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Sean
 */
public class TokenExpiredException extends IllegalArgumentException {

    public TokenExpiredException(String msg) {
        super(msg);
    }

    public TokenExpiredException(String msg, Throwable t) {
        super(msg, t);
    }
}
