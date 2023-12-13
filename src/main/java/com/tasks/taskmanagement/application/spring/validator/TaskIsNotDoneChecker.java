package com.tasks.taskmanagement.application.spring.validator;

import com.tasks.taskmanagement.application.spring.exception.TaskValidationException;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.validator.TaskValidator;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * The TaskIsNotDoneChecker class is a task validator that checks if a task is not marked as DONE.
 */
@Component
@Qualifier("TaskIsNotDoneChecker")
public class TaskIsNotDoneChecker implements TaskValidator {

    /**
     * Validate a task to ensure that it is not marked as DONE.
     *
     * @param task The task to be validated.
     * @throws TaskValidationException If the task is marked as DONE.
     */
    @Override
    public void validate(Task task) {
        if (task.getStatus() == TaskStatus.DONE) {
            throw new TaskValidationException("The task cannot be Done for this operation.");
        }
    }
}
