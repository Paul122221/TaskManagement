package com.tasks.taskmanagement.application.spring.validator;

import com.tasks.taskmanagement.application.spring.exception.TaskValidationException;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.validator.TaskValidator;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The TaskIsNotPastDueOrDeadlineExpired class is a task validator that checks if a task is not marked as PAST_DUE with a non-expired due date and time.
 */
@Component
@Qualifier("TaskIsNotPastDueOrDeadlineExpired")
public class TaskIsNotPastDueOrDeadlineExpired implements TaskValidator {

    /**
     * Validate a task to ensure that it is not marked as PAST_DUE with a non-expired due date and time.
     *
     * @param task The task to be validated.
     * @throws TaskValidationException If the task is marked as PAST_DUE with a non-expired due date and time.
     */
    @Override
    public void validate(Task task) {
        if (
                task.getStatus() == TaskStatus.PAST_DUE
                        && task.getDueDateTime() != null
                        && task.getDueDateTime().isAfter(LocalDateTime.now())
        ) {
            throw new TaskValidationException("The task can't be PAST_DUE with a not expired due date and time.");
        }
    }
}
