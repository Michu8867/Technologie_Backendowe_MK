package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserBasicInfoDto;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // List all users with basic info
    @GetMapping("/basic-info")
    public List<UserBasicInfoDto> getAllUsersBasicInfo() {
        return userService.getAllUsers()
                .stream()
                .map(user -> new UserBasicInfoDto(user.getId(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toList());
    }

    // Find users by email
    @GetMapping("/search")
    public List<UserEmailDto> findUsersByEmail(@RequestParam String email) {
        return userService.findUsersByEmailAddress(email)
                .stream()
                .map(user -> new UserEmailDto(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }

    // Find users older than a given date
    @GetMapping("/older/{date}")
    public List<UserDto> findUsersOlderThan(@PathVariable String date) {
        return userService.findUsersOlderThan(date)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    // CRUD operations

    // Create a new user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getBirthdate() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("All fields are required");
        }
        return userService.createUser(user);
    }

    // Update an existing user by ID
    @PutMapping("/{userId}")
    public User updateUserById(@PathVariable Long userId, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userId, userUpdateDto);
    }

    // Delete a user by ID
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    // Get user details by ID
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userMapper.toDto(userService.getUserById(userId));
    }

    // Get all users
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get all simple users
    @GetMapping("/simple")
    public List<UserBasicInfoDto> getAllSimpleUsers() {
        return userService.getAllUsers()
                .stream()
                .map(user -> new UserBasicInfoDto(user.getId(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toList());
    }

    // Get user by email
    @GetMapping("/email")
    public List<UserDto> getUserByEmail(@RequestParam String email) {
        return userService.findUsersByEmailAddress(email)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}



















