package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
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
    private User updatedUser;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .collect(toList());
    }

    /* Crud operations */
    @PostMapping("/{firstName}/{lastName}/{birthdate}/{email}")
    public void createUserInDatabase(@PathVariable String firstName,
                                     @PathVariable String lastName,
                                     @PathVariable LocalDate birthdate,
                                     @PathVariable String email) {
        userService.createUser(new User(firstName, lastName, birthdate, email));
    }

    // get user by id
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.toDto(userService.getUserById(id));
    }



}