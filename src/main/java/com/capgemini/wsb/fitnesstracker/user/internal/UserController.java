package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(toList());
    }

    // Find users by email
    @GetMapping("/search")
    public List<UserEmailDto> findUsersByEmail(@RequestParam String email) {
        return userService.findUsersByEmail(email);
    }

    // Find users older than
    @GetMapping("/older-than/{age}")
    public List<UserDto> findUsersOlderThan(@PathVariable int age) {
        return userService.findUsersOlderThan(age)
                .stream()
                .map(userMapper::toDto)
                .collect(toList());
    }

    // CRUD operations
    // Create a new user
    @PostMapping("/{firstName}/{lastName}/{birthdate}/{email}")
    public void createUserInDatabase(@PathVariable String firstName,
                                     @PathVariable String lastName,
                                     @PathVariable LocalDate birthdate,
                                     @PathVariable String email) {
        userService.createUser(new User(firstName, lastName, birthdate, email));
    }

    // Get user by id
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.toDto(userService.getUserById(id));
    }

    // Update user by id
    @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        User updatedUser = userService.updateUser(id, userUpdateDto);
        return userMapper.toDto(updatedUser);
    }

    // Delete user by id
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
