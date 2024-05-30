package com.capgemini.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines the methods for retrieving users from the system.
 */
public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with the given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId the ID of the user to be searched
     * @return an {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user based on their email.
     * If the user with the given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email the email of the user to be searched
     * @return an {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return a {@link List} containing all users
     */
    List<User> findAllUsers();

}
