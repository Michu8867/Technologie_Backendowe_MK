package com.capgemini.wsb.fitnesstracker.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled tasks for sending weekly training reports.
 */
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final TrainingReportService trainingReportService;

/**
 * Sends weekly training reports to users.
 */
@Scheduled(cron = "0 0 6 * * MON")
public void sendWeeklyReports() {
    trainingReportService.generateAndSendWeeklyReport();
}
}
