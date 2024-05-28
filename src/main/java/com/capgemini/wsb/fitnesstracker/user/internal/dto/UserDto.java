package com.capgemini.wsb.fitnesstracker.user.internal.dto;

import java.time.LocalDate;

public record UserDto(Long id, String firstName, String lastName, LocalDate birthdate, String email, int age) {}


