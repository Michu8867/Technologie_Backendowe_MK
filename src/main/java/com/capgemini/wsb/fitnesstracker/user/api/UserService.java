package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserUpdateDto;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    List<User> findUsersByEmailAddress(String email);

    List<User> findUsersOlderThan(String date);

    User createUser(User user);

    User updateUser(Long userId, UserUpdateDto userUpdateDto);

    void deleteUserById(Long userId);

    User getUserById(Long userId);
}






