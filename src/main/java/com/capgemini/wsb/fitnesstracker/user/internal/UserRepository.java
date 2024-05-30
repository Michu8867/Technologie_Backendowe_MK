package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds users whose email contains the given fragment, ignoring case.
     *
     * @param email the email fragment to search for
     * @return a list of users with matching email fragments
     */
    List<User> findByEmailContainingIgnoreCase(String email);

    /**
     * Finds users who were born before the specified date.
     *
     * @param date the date to compare against
     * @return a list of users born before the specified date
     */
    List<User> findByBirthdateBefore(LocalDate date);
}






