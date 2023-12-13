package com.tasks.taskmanagement.application.spring.validator;

import com.tasks.taskmanagement.application.spring.exception.TaskValidationException;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.validator.TaskValidator;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * The TaskIsNotPastDueChecker class is a task validator that checks if a task is not marked as PAST_DUE.
 */
@Component
@Qualifier("TaskIsNotPastDueChecker")
public class TaskIsNotPastDueChecker implements TaskValidator {

    /**
     * Validate a task to ensure that it is not marked as PAST_DUE.
     *
     * @param task The task to be validated.
     * @throws TaskValidationException If the task is marked as PAST_DUE.
     */
    @Override
    public void validate(Task task) {
        if (task.getStatus() == TaskStatus.PAST_DUE) {
            throw new TaskValidationException("The task cannot be PAST_DUE for this operation.");
        }
    }
}
