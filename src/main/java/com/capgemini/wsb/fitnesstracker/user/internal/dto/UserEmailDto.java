package com.capgemini.wsb.fitnesstracker.user.internal.dto;

/**
 * A DTO (Data Transfer Object) that contains the ID and email of a user.
 *
 * @param id    the unique identifier of the user
 * @param email the email address of the user
 */
public record UserEmailDto(Long id, String email) {}
