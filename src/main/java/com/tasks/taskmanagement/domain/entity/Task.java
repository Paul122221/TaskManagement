package com.tasks.taskmanagement.domain.entity;

import com.tasks.taskmanagement.domain.valueobject.TaskStatus;

import java.time.LocalDateTime;

/**
 * The Task interface defines the properties and methods for managing task entities.
 */
public interface Task {

    /**
     * Get the ID of the task.
     *
     * @return The task's unique ID.
     */
    Long getId();

    /**
     * Set the ID of the task.
     *
     * @param id The unique ID to set for the task.
     */
    void setId(Long id);

    /**
     * Get the description of the task.
     *
     * @return The description of the task.
     */
    String getDescription();

    /**
     * Set the description of the task.
     *
     * @param description The description to set for the task.
     */
    void setDescription(String description);

    /**
     * Get the status of the task.
     *
     * @return The status of the task (e.g., NOT_DONE, DONE, PAST_DUE).
     */
    TaskStatus getStatus();

    /**
     * Set the status of the task.
     *
     * @param status The status to set for the task.
     */
    void setStatus(TaskStatus status);

    /**
     * Get the creation date and time of the task.
     *
     * @return The date and time when the task was created.
     */
    LocalDateTime getCreationDateTime();

    /**
     * Set the creation date and time of the task.
     *
     * @param creationDateTime The date and time when the task was created.
     */
    void setCreationDateTime(LocalDateTime creationDateTime);

    /**
     * Get the due date and time of the task.
     *
     * @return The due date and time of the task.
     */
    LocalDateTime getDueDateTime();

    /**
     * Set the due date and time of the task.
     *
     * @param dueDateTime The due date and time for the task.
     */
    void setDueDateTime(LocalDateTime dueDateTime);

    /**
     * Get the completion date and time of the task.
     *
     * @return The completion date and time of the task.
     */
    LocalDateTime getCompletionDateTime();

    /**
     * Set the completion date and time of the task.
     *
     * @param completionDateTime The completion date and time for the task.
     */
    void setCompletionDateTime(LocalDateTime completionDateTime);
}
