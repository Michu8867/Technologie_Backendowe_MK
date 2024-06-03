package com.capgemini.wsb.fitnesstracker.training.api;

import java.util.List;

/**
 * Interface for providing training services.
 */
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

