package com.tasks.taskmanagement.application.spring.validator;

import com.tasks.taskmanagement.application.spring.exception.TaskValidationException;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.validator.TaskValidator;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The TaskIsDoneInTheFuture class is a task validator that checks if a task is marked as DONE with a future completion date and time.
 */
@Component
@Qualifier("TaskIsDoneInTheFuture")
public class TaskIsDoneInTheFuture implements TaskValidator {

    /**
     * Validate a task to ensure that it is not marked as DONE with a future completion date and time.
     *
     * @param task The task to be validated.
     * @throws TaskValidationException If the task is marked as DONE with a future completion date and time.
     */
    @Override
    public void validate(Task task) {
        if (
                task.getStatus() == TaskStatus.DONE
                        && task.getCompletionDateTime() != null
                        && task.getCompletionDateTime().isAfter(LocalDateTime.now())
        ) {
            throw new TaskValidationException("The task cannot be DONE in the future time.");
        }
    }
}
