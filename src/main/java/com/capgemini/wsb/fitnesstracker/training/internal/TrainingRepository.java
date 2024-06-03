package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing training data.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Finds trainings by user ID.
     *
     * @param userId the ID of the user
     * @return a list of trainings for the specified user
     */
    List<Training> findByUserId(Long userId);

    /**
     * Finds trainings that ended after a specific time.
     *
     * @param afterTime the time after which to find trainings
     * @return a list of trainings that ended after the specified time
     */
    List<Training> findByEndTimeAfter(LocalDateTime afterTime);

    /**
     * Finds trainings by activity type.
     *
     * @param activityType the type of activity
     * @return a list of trainings of the specified activity type
     */
    List<Training> findByActivityType(ActivityType activityType);
}
