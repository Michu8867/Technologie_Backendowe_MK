package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    Training createTraining(Training training);
    List<Training> getAllTrainings();
    List<Training> getTrainingsByUser(User user);
    List<Training> getCompletedTrainings(Date afterTime);
    List<Training> getTrainingsByActivityType(ActivityType activityType);
    Training updateTraining(Long trainingId, Training updatedTraining);
}
