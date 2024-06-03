package com.capgemini.wsb.fitnesstracker.user.internal.controller;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserBasicInfoDto;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing users in the Fitness Tracker system.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Retrieves basic information (ID, first name, last name) for all users.
     *
     * @return a list of {@link UserBasicInfoDto} containing basic user information
     */
    @GetMapping("/basic-info")
    public List<UserBasicInfoDto> getAllUsersBasicInfo() {
        return userService.getAllUsers()
                .stream()
                .map(user -> new UserBasicInfoDto(user.getId(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toList());
    }

    /**
     * Finds users by email address.
     *
     * @param email the email to search for
     * @return a list of {@link UserEmailDto} containing users' ID and email
     */
    @GetMapping("/search")
    public List<UserEmailDto> findUsersByEmail(@RequestParam String email) {
        return userService.findUsersByEmailAddress(email)
                .stream()
                .map(user -> new UserEmailDto(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Finds users older than a given date.
     *
     * @param date the date to compare
     * @return a list of {@link UserDto} containing users' details
     */
    @GetMapping("/older/{date}")
    public List<UserDto> findUsersOlderThan(@PathVariable String date) {
        return userService.findUsersOlderThan(date)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created {@link User}
     * @throws IllegalArgumentException if any required fields are null
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getBirthdate() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("All fields are required");
        }
        return userService.createUser(user);
    }

    @PostMapping("/{firstname}/{lastname}/{birthdate}/{email}")
    public void addUserToDatabase(@PathVariable("firstname") String firstname,
                                  @PathVariable("lastname") String lastname,
                                  @PathVariable("birthdate") LocalDate birthdate,
                                  @PathVariable("email") String email) {

        User user = new User(firstname, lastname, birthdate, email);
        userService.createUser(user);

    }

    /**
     * Updates an existing user by ID.
     *
     * @param userId the ID of the user to update
     * @param userUpdateDto the user data to update
     * @return the updated {@link User}
     */
    @PutMapping("/{userId}")
    public User updateUserById(@PathVariable Long userId, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userId, userUpdateDto);
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId the ID of the user to delete
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    /**
     * Retrieves detailed information about a user by ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the {@link UserDto} containing the user's details
     */
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userMapper.toDto(userService.getUserById(userId));
    }

    /**
     * Retrieves all users with detailed information.
     *
     * @return a list of {@link UserDto} containing users' details
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all users with basic information.
     *
     * @return a list of {@link UserBasicInfoDto} containing basic user information
     */
    @GetMapping("/simple")
    public List<UserBasicInfoDto> getAllSimpleUsers() {
        return userService.getAllUsers()
                .stream()
                .map(user -> new UserBasicInfoDto(user.getId(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves users by email.
     *
     * @param email the email to search for
     * @return a list of {@link UserDto} containing users' details
     * @throws UserNotFoundException if no user with the given email is found
     */
    @GetMapping("/email")
    public List<UserDto> getUserByEmail(@RequestParam String email) {
        List<User> users = userService.findUsersByEmailAddress(email);
        if (users.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
