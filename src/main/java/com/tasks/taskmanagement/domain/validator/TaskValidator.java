package com.tasks.taskmanagement.domain.validator;

import com.tasks.taskmanagement.domain.entity.Task;

/**
 * The TaskValidator interface defines a contract for validating tasks.
 */
public interface TaskValidator {

    /**
     * Validates the given task.
     *
     * @param task The task to be validated.
     * @throws RuntimeException if the task fails validation.
     */
    void validate(Task task);
}
