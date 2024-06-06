package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for generating weekly training reports.
 */
@Service
@RequiredArgsConstructor
public class TrainingReportService {

    private final TrainingProvider trainingProvider;
    private final UserService userService;
    private final EmailSender emailSender;

    /**
     * Generates a weekly report of training sessions for each user and sends it via email.
     */
    public void generateAndSendWeeklyReport() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.minus(1, ChronoUnit.WEEKS);

        List<User> users = userService.getAllUsers();
        Map<User, Long> report = users.stream().collect(Collectors.toMap(
                user -> user,
                user -> trainingProvider.getAllTrainingsForUser(user.getId()).stream()
                        .filter(training -> training.getStartTime().after(java.sql.Date.valueOf(startOfWeek)))
                        .count()
        ));

        report.forEach((user, count) -> {
            String emailContent = String.format("Hi %s, you had %d trainings last week.", user.getFirstName(), count);
            EmailDto email = new EmailDto(user.getEmail(), "Weekly Training Report", emailContent);
            emailSender.send(email);
        });
    }
}
