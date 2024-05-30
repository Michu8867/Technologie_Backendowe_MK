package com.capgemini.wsb.fitnesstracker.user.internal.dto;

/**
 * A DTO (Data Transfer Object) that contains basic information about a user.
 *
 * @param id        the unique identifier of the user
 * @param firstName the first name of the user
 * @param lastName  the last name of the user
 */
public record UserBasicInfoDto(Long id, String firstName, String lastName) {}
