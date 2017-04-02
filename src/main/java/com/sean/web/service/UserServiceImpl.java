package com.sean.web.service;

import com.sean.web.model.User;
import com.sean.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
/**
 * @author Sean
 */
public class UserServiceImpl implements UserService {

    /**
     * The {@link UserRepository} used to receive, update or insert a {@link User} into the database.
     */
    private final UserRepository userRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
