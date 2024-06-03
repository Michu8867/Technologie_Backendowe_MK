package com.capgemini.wsb.fitnesstracker.training.internal;

/**
 * Enum representing different types of activities.
 */
public enum ActivityType {

    RUNNING("Running"),
    CYCLING("Cycling"),
    WALKING("Walking"),
    SWIMMING("Swimming"),
    TENNIS("Tennis");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the activity type.
     *
     * @return the display name of the activity type
     */
    public String getDisplayName() {
        return displayName;
    }

}
