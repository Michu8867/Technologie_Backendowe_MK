package com.capgemini.wsb.fitnesstracker.user.api;

/**
 * Exception thrown when a user is not found in the system.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with a specified user ID.
     *
     * @param id the ID of the user that was not found
     */
    public UserNotFoundException(Long id) {
        super("User not found with ID: " + id);
    }

    /**
     * Constructs a new UserNotFoundException with a specified email.
     *
     * @param email the email of the user that was not found
     */
    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }
}


