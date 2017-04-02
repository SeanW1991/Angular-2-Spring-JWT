package com.sean.web.config;

/**
 * Created by sean on 01/04/2017.
 */
public final class JwtConstants {

    /**
     * Private constructor so the class cannot be created.
     */
    private JwtConstants() {

    }

    /**
     * The name create the claims.
     */
    public static final String CLAIMS_NAME = "scopes";

    /**
     * The header for the access token.
     */
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";

    /**
     * The header for the refresh token.
     */
    public static final String JWT_REFRESH_TOKEN_HEADER_PARAM = "R-Authorization";

    /**
     * The login authentication restful directory.
     */
    public static final String LOGIN_LOCATION = "/api/auth/login";

    /**
     * The location of all api directories.
     */
    public static final String API_LOCATION = "/api/**";

    /**
     * The refresh token directory.
     */
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/token/refresh";

}
