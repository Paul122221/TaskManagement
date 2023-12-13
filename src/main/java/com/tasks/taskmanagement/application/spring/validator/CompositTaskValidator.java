package com.tasks.taskmanagement.application.spring.validator;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.validator.TaskValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * The CompositTaskValidator class implements the TaskValidator interface.
 * It is responsible for combining multiple task validators into a composite validator.
 */
public class CompositTaskValidator implements TaskValidator {
    private final List<TaskValidator> validators = new ArrayList<>();

    /**
     * Add a task validator to the composite validator.
     *
     * @param validator The task validator to add to the composite validator.
     */
    public void addValidator(TaskValidator validator) {
        validators.add(validator);
    }

    /**
     * Validate a task by applying all the registered validators.
     *
     * @param task The task to be validated.
     */
    @Override
    public void validate(Task task) {
        for (TaskValidator validator : validators) {
            validator.validate(task);
        }
    }
}
