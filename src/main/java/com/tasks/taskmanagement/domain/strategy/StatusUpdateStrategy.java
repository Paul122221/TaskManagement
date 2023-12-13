package com.tasks.taskmanagement.domain.strategy;

import com.tasks.taskmanagement.domain.entity.Task;

/**
 * The StatusUpdateStrategy interface defines a strategy for updating the status of a task.
 */
public interface StatusUpdateStrategy {

    /**
     * Determines whether the status of a given task should be updated according to the strategy.
     *
     * @param task The task to be evaluated for status update.
     * @return `true` if the status should be updated, `false` otherwise.
     */
    boolean shouldUpdate(Task task);
}
