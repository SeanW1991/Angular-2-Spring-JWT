package com.sean.web.authentication.login;

/**
 * Created by sean on 01/04/2017.
 *
 * The response the login will return if authentication is correct.
 */
public final class LoginAuthenticationResponse {

    /**
     * The access token.
     */
    private final String token;

    /**
     * The refresh token.
     */
    private final String refreshToken;

    /**
     * Creates a new {@link LoginAuthenticationResponse}.
     * @param token The access token.
     * @param refreshToken The refresh token.
     */
    public LoginAuthenticationResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    /**
     * Gets the access {@code token}.
     * @return The {@code token}.
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the refresh {@code token}.
     * @return The {@code refreshToken}.
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}
