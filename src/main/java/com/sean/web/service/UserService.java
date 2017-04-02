package com.sean.web.service;

import com.sean.web.model.User;

import java.util.Optional;

/**
 * @author Sean
 *
 * An interface used for both proxying the {@link com.sean.web.repository.UserRepository} and for any
 * mutation of user.
 */
public interface UserService {

    /**
     * Finds a {@link User} based on its username.
     * @param username The username of the user.
     * @return Returns an {@link Optional} either containing the prenent {@link User}
     * or an empty {@link Optional#empty()}.
     */
    public Optional<User> findByUsername(String username);
}
