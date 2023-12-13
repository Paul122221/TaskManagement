package com.tasks.taskmanagement.domain.valueobject;

/**
 * The TaskStatus enum represents the status of a task, which can be one of the following values:
 * - NOT_DONE: Task is not completed.
 * - DONE: Task is completed.
 * - PAST_DUE: Task is past its due date and not completed.
 */
public enum TaskStatus {
    NOT_DONE("not done"),
    DONE("done"),
    PAST_DUE("past due");

    private final String value;

    /**
     * Constructs a TaskStatus enum value with the specified string representation.
     *
     * @param value The string representation of the task status.
     */
    TaskStatus(String value) {
        this.value = value;
    }

    /**
     * Get the string representation of the task status.
     *
     * @return The string representation of the task status.
     */
    public String getValue() {
        return value;
    }

    /**
     * Convert a string value to a TaskStatus enum value.
     *
     * @param value The string value to convert.
     * @return The corresponding TaskStatus enum value.
     * @throws IllegalArgumentException if the provided string value is invalid.
     */
    public static TaskStatus fromValue(String value) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid TaskStatus value: " + value);
    }
}
