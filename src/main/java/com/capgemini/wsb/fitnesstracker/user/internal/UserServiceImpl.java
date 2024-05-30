package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the {@link UserService} interface.
 * Provides methods for managing users in the fitness tracker application.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Finds users whose email contains the given fragment, ignoring case.
     *
     * @param email the email fragment to search for
     * @return a list of users with matching email fragments
     */
    @Override
    public List<User> findUsersByEmailAddress(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    /**
     * Finds users who are older than a given date.
     *
     * @param date the date to compare against
     * @return a list of users born before the specified date
     */
    @Override
    public List<User> findUsersOlderThan(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return userRepository.findByBirthdateBefore(localDate);
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user
     */
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates an existing user by ID.
     *
     * @param userId the ID of the user to update
     * @param userUpdateDto the updated user data
     * @return the updated user
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    public User updateUser(Long userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setEmail(userUpdateDto.getEmail());
        user.setBirthdate(userUpdateDto.getBirthdate());
        return userRepository.save(user);
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId the ID of the user to delete
     */
    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the retrieved user
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
