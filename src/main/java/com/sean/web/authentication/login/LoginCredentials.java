package com.sean.web.authentication.login;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sean
 *
 * A dedicated class that contains the credentials create the username and password request.
 */
public class LoginCredentials {

    /**
     * The requested username.
     */
    private String username;

    /**
     * The requested password.
     */
    private String password;

    /**
     * Creates a new {@link LoginCredentials} containing the username and password create the requested user.
     * @param username The username attempting to login.
     * @param password The password create the attempted username.
     */
    @JsonCreator
    public LoginCredentials(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the requested {@code username}.
     * @return The {@code username}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the requested {@code password}.
     * @return The {@code password}.
     */
    public String getPassword() {
        return password;
    }
}
