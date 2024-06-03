package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.Data;

import java.util.Date;

/**
 * Data transfer object for training requests.
 */
@Data
public class TrainingRequest {
    private Long userId;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;
}
