package com.tasks.taskmanagement.application.spring.validator;

import com.tasks.taskmanagement.application.spring.exception.TaskValidationException;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.validator.TaskValidator;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The TaskIsNotNotDoneOrDeadlineNotExpired class is a task validator that checks if a task is not marked as NOT_DONE with an expired due date and time.
 */
@Component
@Qualifier("TaskIsNotNotDoneOrDeadlineNotExpired")
public class TaskIsNotNotDoneOrDeadlineNotExpired implements TaskValidator {

    /**
     * Validate a task to ensure that it is not marked as NOT_DONE with an expired due date and time.
     *
     * @param task The task to be validated.
     * @throws TaskValidationException If the task is marked as NOT_DONE with an expired due date and time.
     */
    @Override
    public void validate(Task task) {
        if (
                task.getStatus() == TaskStatus.NOT_DONE
                        && task.getDueDateTime() != null
                        && task.getDueDateTime().isBefore(LocalDateTime.now())
        ) {
            throw new TaskValidationException("The task cannot be NOT_DONE with an expired due date and time.");
        }
    }
}
