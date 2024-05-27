package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.internal.UserEmailDto;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserUpdateDto userUpdateDto);
    void deleteUserById(Long id);
    List<User> findUsersByEmailAddress(String emailAddresses);
    List<User> findUsersOlderThan(int age);
    List<User> findAllUsers();

    List<UserEmailDto> findUsersByEmail(String email);
}
