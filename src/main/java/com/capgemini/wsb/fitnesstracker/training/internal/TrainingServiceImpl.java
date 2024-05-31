package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final UserService userService;

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getAllTrainingsForUser(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    @Override
    public List<Training> getAllFinishedTrainingsAfterTime(String afterTime) {
        LocalDate date = LocalDate.parse(afterTime);
        return trainingRepository.findByEndTimeAfter(date.atStartOfDay());
    }

    @Override
    public List<Training> getAllTrainingByActivityType(String activityType) {
        return trainingRepository.findByActivityType(ActivityType.valueOf(activityType));
    }

    @Override
    public Training createTraining(Training training) {
        validateUser(training.getUser().getId());
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Long trainingId, Training training) {
        validateUser(training.getUser().getId());
        Training existingTraining = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found with ID: " + trainingId));
        existingTraining.setUser(training.getUser());
        existingTraining.setStartTime(training.getStartTime());
        existingTraining.setEndTime(training.getEndTime());
        existingTraining.setActivityType(training.getActivityType());
        existingTraining.setDistance(training.getDistance());
        existingTraining.setAverageSpeed(training.getAverageSpeed());
        return trainingRepository.save(existingTraining);
    }

    @Override
    public void deleteTrainingById(Long trainingId) {
        trainingRepository.deleteById(trainingId);
    }

    @Override
    public Training getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found with ID: " + trainingId));
    }

    private void validateUser(Long userId) {
        if (userId == null || userService.getUserById(userId) == null) {
            throw new IllegalArgumentException("User must be provided with a valid ID");
        }
    }
}
