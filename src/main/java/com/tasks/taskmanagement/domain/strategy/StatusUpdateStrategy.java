package com.tasks.taskmanagement.domain.strategy;

import com.tasks.taskmanagement.domain.entity.Task;

public interface StatusUpdateStrategy {
    boolean shouldUpdate(Task task);
}
