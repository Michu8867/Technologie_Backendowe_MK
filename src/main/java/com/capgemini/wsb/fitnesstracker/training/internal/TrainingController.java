package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingProvider trainingService;
    private final UserService userService;

    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{userId}")
    public List<Training> getAllTrainingsForUser(@PathVariable Long userId) {
        return trainingService.getAllTrainingsForUser(userId);
    }

    @GetMapping("/finished/{afterTime}")
    public List<Training> getAllFinishedTrainingsAfterTime(@PathVariable String afterTime) {
        return trainingService.getAllFinishedTrainingsAfterTime(afterTime);
    }

    @GetMapping("/activityType")
    public List<Training> getAllTrainingByActivityType(@RequestParam String activityType) {
        return trainingService.getAllTrainingByActivityType(activityType);
    }

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
                trainingRequest.getActivityType(),
                trainingRequest.getDistance(),
                trainingRequest.getAverageSpeed()
        );

        return trainingService.createTraining(training);
    }

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
                trainingRequest.getActivityType(),
                trainingRequest.getDistance(),
                trainingRequest.getAverageSpeed()
        );

        return trainingService.updateTraining(trainingId, training);
    }

    @DeleteMapping("/{trainingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTraining(@PathVariable Long trainingId) {
        trainingService.deleteTrainingById(trainingId);
    }
}
