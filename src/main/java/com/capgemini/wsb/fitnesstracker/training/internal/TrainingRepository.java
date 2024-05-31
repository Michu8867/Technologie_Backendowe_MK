package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByUserId(Long userId);
    List<Training> findByEndTimeAfter(LocalDateTime afterTime);
    List<Training> findByActivityType(ActivityType activityType);
}
