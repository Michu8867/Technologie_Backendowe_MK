package com.capgemini.wsb.fitnesstracker.user.api;

import java.util.List;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserUpdateDto userUpdateDto);

    void deleteUserById(Long id);
    List<User> findUsersByEmailAddress(String emailAddresses);
    //List<User> findUsersOlderThan(int age);

    List<User> findAllUsers();
}
