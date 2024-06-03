package com.capgemini.wsb.fitnesstracker.training.internal.controller;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRequest;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * REST controller for managing training sessions.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);

    private final TrainingProvider trainingService;
    private final UserService userService;

    /**
     * Retrieves all training sessions.
     *
     * @return a list of all training sessions
     */
    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    /**
     * Retrieves all training sessions for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of training sessions for the specified user
     */
    @GetMapping("/{userId}")
    public List<Training> getAllTrainingsForUser(@PathVariable Long userId) {
        return trainingService.getAllTrainingsForUser(userId);
    }

    /**
     * Retrieves all finished training sessions after a specific time.
     *
     * @param afterTime the time after which to retrieve finished training sessions
     * @return a list of finished training sessions after the specified time
     */
    @GetMapping("/finished/{afterTime}")
    public List<Training> getAllFinishedTrainingsAfterTime(@PathVariable String afterTime) {
        return trainingService.getAllFinishedTrainingsAfterTime(afterTime);
    }

    /**
     * Retrieves all training sessions by activity type.
     *
     * @param activityType the type of activity
     * @return a list of training sessions by the specified activity type
     */
    @GetMapping("/activityType")
    public List<Training> getAllTrainingByActivityType(@RequestParam String activityType) {
        return trainingService.getAllTrainingByActivityType(activityType);
    }

    /**
     * Creates a new training session.
     *
     * @param trainingRequest the training request
     * @return the created training session
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training createTraining(@RequestBody TrainingRequest trainingRequest) {
        User user = userService.getUserById(trainingRequest.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User must be provided with a valid ID");
        }

        Training training = new Training(
                user,
                trainingRequest.getStartTime(),
                trainingRequest.getEndTime(),
                ActivityType.valueOf(String.valueOf(trainingRequest.getActivityType())),
                trainingRequest.getDistance(),
                trainingRequest.getAverageSpeed()
        );

        return trainingService.createTraining(training);
    }

    /**
     * Creates a new training session using path variables.
     *
     * @param userId the ID of the user
     * @param startTime the start time of the training
     * @param endTime the end time of the training
     * @param activityType the type of activity
     * @param distance the distance covered
     * @param averageSpeed the average speed
     */
    @PostMapping("/{userId}/{startTime}/{endTime}/{activityType}/{distance}/{averageSpeed}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTrainingToDatabase(@PathVariable("userId") Long userId,
                                      @PathVariable("startTime") String startTime,
                                      @PathVariable("endTime") String endTime,
                                      @PathVariable("activityType") String activityType,
                                      @PathVariable("distance") double distance,
                                      @PathVariable("averageSpeed") double averageSpeed) {

        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User must be provided with a valid ID");
            }

            Date start = Date.from(LocalDateTime.parse(startTime).atZone(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(LocalDateTime.parse(endTime).atZone(ZoneId.systemDefault()).toInstant());

            ActivityType type;
            try {
                type = ActivityType.valueOf(activityType);
            } catch (IllegalArgumentException e) {
                logger.error("Invalid activity type: {}", activityType);
                throw new IllegalArgumentException("Invalid activity type: " + activityType);
            }

            Training training = new Training(user, start, end, type, distance, averageSpeed);
            trainingService.createTraining(training);
        } catch (Exception e) {
            logger.error("Error creating training", e);
            throw new RuntimeException("Error creating training: " + e.getMessage());
        }
    }

    /**
     * Updates an existing training session.
     *
     * @param trainingId the ID of the training session to update
     * @param trainingRequest the updated training details
     * @return the updated training session
     */
    @PutMapping("/{trainingId}")
    public Training updateTraining(@PathVariable Long trainingId, @RequestBody TrainingRequest trainingRequest) {
        User user = userService.getUserById(trainingRequest.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User must be provided with a valid ID");
        }

        Training training = new Training(
                user,
                trainingRequest.getStartTime(),
                trainingRequest.getEndTime(),
                ActivityType.valueOf(String.valueOf(trainingRequest.getActivityType())),
                trainingRequest.getDistance(),
                trainingRequest.getAverageSpeed()
        );

        return trainingService.updateTraining(trainingId, training);
    }

    /**
     * Deletes a training session.
     *
     * @param trainingId the ID of the training session to delete
     */
    @DeleteMapping("/{trainingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTraining(@PathVariable Long trainingId) {
        trainingService.deleteTrainingById(trainingId);
    }
}
