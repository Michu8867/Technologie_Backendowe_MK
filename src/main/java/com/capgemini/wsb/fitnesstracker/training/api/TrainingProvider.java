package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.List;

public interface TrainingProvider {

    List<Training> getAllTrainings();

    List<Training> getAllTrainingsForUser(Long userId);

    List<Training> getAllFinishedTrainingsAfterTime(String afterTime);

    List<Training> getAllTrainingByActivityType(String activityType);

    Training createTraining(Training training);

    Training updateTraining(Long trainingId, Training training);

    void deleteTrainingById(Long trainingId);

    Training getTraining(Long trainingId);
}

