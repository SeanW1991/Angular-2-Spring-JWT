package com.sean.web.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="APP_USER")
/**
 * @author Sean
 *
 * The dedicated class used to represent a user within the database.
 */
public class User {

    /**
     * The id column name.
     */
    private static final String ID_COLUMN_NAME = "ID";

    /**
     * The username column name.
     */
    private static final String USERNAME_COLUMN_NAME = "username";

    /**
     * The password column name.
     */
    private static final String PASSWORD_COLUMN_NAME = "password";

    /**
     * Sets the id as the index in the database,
     * sets the id to be automatically generated and
     * sets the column name to the id column.
     */
    @Id
    @Column(name=ID_COLUMN_NAME)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Sets the username to use the username column.
     */
    @Column(name=USERNAME_COLUMN_NAME )
    private String username;

    /**
     * Sets the password to use the password column.
     */
    @Column(name=PASSWORD_COLUMN_NAME)
    private String password;

    /**
     * Creates a one to many relationship between the user and their role because
     * a single user can have multiple roles.
     */
    @OneToMany
    @JoinColumn(name=UserRole.APP_USER_ID_COLUMN_NAME, referencedColumnName=ID_COLUMN_NAME)
    private List<UserRole> roles;

    /**
     * Private constructor for hibernate use.
     */
    private User() { }

    /**
     * Creates a new User with the required attributes.
     * @param id The id of the user within the database.
     * @param username The username.
     * @param password The password.
     * @param roles The list of roles.
     */
    public User(Long id, String username, String password, List<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    /**
     * Gets the id of the user within the database,
     * @return The {@code id}.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the username.
     * @return The {@code username.}
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     * @return The {@code password}
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the assigned roles of the user.
     * @return The {@code roles}.
     */
    public List<UserRole> getRoles() {
        return roles;
    }
}
