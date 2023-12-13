package com.tasks.taskmanagement.application.spring.service.statusupdate;

import com.tasks.taskmanagement.domain.entity.Task;

/**
 * The TaskStatusUpdater interface defines methods for updating the status of tasks.
 */
public interface TaskStatusUpdater {

    /**
     * Update the status of a specific task.
     *
     * @param task The task to update the status for.
     */
    void updateStatus(Task task);

    /**
     * Update the status of all tasks. Implementations of this method should iterate through
     * all tasks and update their statuses as needed.
     */
    void updateStatusAll();
}
