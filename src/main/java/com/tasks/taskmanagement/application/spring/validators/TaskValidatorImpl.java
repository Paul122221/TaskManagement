package com.tasks.taskmanagement.application.spring.validators;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskValidatorImpl implements TaskValidator {
    @Override
    public boolean validate(Task task) {
        return task.getStatus() != TaskStatus.PAST_DUE;
    }
}