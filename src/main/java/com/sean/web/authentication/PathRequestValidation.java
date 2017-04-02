package com.sean.web.authentication;

import com.sean.web.config.JwtConstants;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sean on 01/04/2017.
 *
 *
 * An implementation create the {@link RequestMatcher} that is designed to skip specific paths.
 */
public final class PathRequestValidation implements RequestMatcher {

    /**
     * The array create {@link AntPathRequestMatcher} used to skip specific paths for authentication.
     */
    private static final AntPathRequestMatcher[] SKIPABLE_PATHS = {
            new AntPathRequestMatcher(JwtConstants.TOKEN_REFRESH_ENTRY_POINT),
            new AntPathRequestMatcher(JwtConstants.LOGIN_LOCATION)
    };

    /**
     * The array create {@link AntPathRequestMatcher} create allowed paths.
     */
    private static final AntPathRequestMatcher[] ALLOWED_PATHS = {
            new AntPathRequestMatcher(JwtConstants.API_LOCATION)
    };

    @Override
    public boolean matches(HttpServletRequest request) {
        for (AntPathRequestMatcher rm : SKIPABLE_PATHS) {
            if (rm.matches(request)) {
                return false;
            }
        }

        for (AntPathRequestMatcher rm : ALLOWED_PATHS) {
            if (rm.matches(request)) {
                return true;
            }
        }

        return false;
    }

}