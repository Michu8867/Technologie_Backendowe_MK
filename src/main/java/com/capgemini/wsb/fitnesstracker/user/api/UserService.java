package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserUpdateDto;
import java.util.List;

/**
 * This interface defines the service methods for managing users in the system.
 */
public interface UserService {

    /**
     * Retrieves all users from the system.
     *
     * @return a {@link List} containing all users
     */
    List<User> getAllUsers();

    /**
     * Finds users by their email address.
     *
     * @param email the email address to search for
     * @return a {@link List} of users with matching email addresses
     */
    List<User> findUsersByEmailAddress(String email);

    /**
     * Finds users older than a certain age.
     *
     * @param date the cutoff date to compare against
     * @return a {@link List} of users older than the specified age
     */
    List<User> findUsersOlderThan(String date);

    /**
     * Creates a new user in the system.
     *
     * @param user the user to be created
     * @return the created user
     */
    User createUser(User user);

    /**
     * Updates an existing user in the system.
     *
     * @param userId the ID of the user to be updated
     * @param userUpdateDto the DTO containing the user's updated information
     * @return the updated user
     */
    User updateUser(Long userId, UserUpdateDto userUpdateDto);

    /**
     * Deletes a user from the system by their ID.
     *
     * @param userId the ID of the user to be deleted
     */
    void deleteUserById(Long userId);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return the user with the specified ID
     */
    User getUserById(Long userId);
}





