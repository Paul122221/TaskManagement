package com.tasks.taskmanagement.application.spring.validators;

import com.tasks.taskmanagement.domain.entity.Task;

public interface TaskValidator {
    boolean validate(Task task);
}
