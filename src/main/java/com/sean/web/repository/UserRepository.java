package com.sean.web.repository;

import com.sean.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Sean
 *
 * @Credits to Vladimir Stankovic for the database code, I used his example.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u left join fetch u.roles r where u.username=:username")
    public Optional<User> findByUsername(@Param("username") String username);
}
