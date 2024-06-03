package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;

/**
 * Exception indicating that the {@link Training} was not found.
 */
@SuppressWarnings("squid:S110")
public class TrainingNotFoundException extends NotFoundException {

    private TrainingNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception indicating that the training with the specified ID was not found.
     *
     * @param id the ID of the training that was not found
     */
    public TrainingNotFoundException(Long id) {
        this("Training with ID=%s was not found".formatted(id));
    }
}
