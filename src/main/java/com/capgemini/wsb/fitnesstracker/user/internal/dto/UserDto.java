package com.capgemini.wsb.fitnesstracker.user.internal.dto;

import java.time.LocalDate;

/**
 * A DTO (Data Transfer Object) that contains detailed information about a user.
 *
 * @param id        the unique identifier of the user
 * @param firstName the first name of the user
 * @param lastName  the last name of the user
 * @param birthdate the birthdate of the user
 * @param email     the email address of the user
 * @param age       the age of the user
 */
public record UserDto(Long id, String firstName, String lastName, LocalDate birthdate, String email, int age) {}
