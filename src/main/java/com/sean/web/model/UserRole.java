package com.sean.web.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = UserRole.USER_ROLE_TABLE)
/**
 * @author Sean
 *
 * A dedicated class used to represent the {@link Role}s a user within the database has.
 */
public class UserRole {

    /**
     * The user role table.
     */
    public static final String USER_ROLE_TABLE = "USER_ROLE";

    /**
     * The user id for the reference inside the database.
     */
    public static final String APP_USER_ID_COLUMN_NAME = "APP_USER_ID";

    /**
     * The role column.
     */
    public static final String ROLE_COLUMN_NAME = "ROLE";

    @Embeddable
    public static class RoleTable implements Serializable {

        /**
         * The id of the user.
         */
        @Column(name = APP_USER_ID_COLUMN_NAME)
        private Long userId;

        /**
         * Sets the enum to be used as a string in the database
         * amd ises the role column.
         */
        @Enumerated(EnumType.STRING)
        @Column(name = ROLE_COLUMN_NAME)
        private Role role;

        /**
         * Private constructor, only required for hibernate.
         */
        private RoleTable() { }

        /**
         * Creates a new {@link RoleTable}.
         * @param userId The index of the user in the database.
         * @param role The role of the user.
         */
        public RoleTable(Long userId, Role role) {
            this.userId = userId;
            this.role = role;
        }
    }

    @EmbeddedId
    RoleTable id = new RoleTable();


    @Enumerated(EnumType.STRING)
    @Column(name = ROLE_COLUMN_NAME, insertable=false, updatable=false)
    private Role role;

    /**
     * Gets the {@link Role}.
     * @return The {@code role}.
     */
    public Role getRole() {
        return role;
    }
}
