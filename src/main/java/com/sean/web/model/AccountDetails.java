package com.sean.web.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.List;

/**
 * @author Sean
 *
 *
 * A dedicated class containing the vital information of a user.
 */
public final class AccountDetails {

    /**
     * The username of the user.
     */
    private final String username;

    /**
     * The {@link List} of {@link GrantedAuthority}s the user is allocated.
     */
    private final List<GrantedAuthority> authorities;

    /**
     * Creates a new {@link AccountDetails}.
     * @param username The username.
     * @param authorities The {@link List} of {@link GrantedAuthority}s.
     */
    public AccountDetails(String username, List<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    /**
     * Creates a new {@link AccountDetails} with a username but an empty {@link List} of authorities.
     * @param username The username.
     */
    public AccountDetails(String username) {
        this(username, Collections.emptyList());
    }

    /**
     * Gets the username.
     * @return The {@code username}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the {@link List} of {@link GrantedAuthority}s.
     * @return The {@code authorities}.
     */
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
