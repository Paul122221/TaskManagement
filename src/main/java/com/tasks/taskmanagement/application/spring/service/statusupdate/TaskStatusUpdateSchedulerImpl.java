package com.tasks.taskmanagement.application.spring.service.statusupdate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * The TaskStatusUpdateSchedulerImpl class implements the TaskStatusUpdateScheduler interface.
 * It is responsible for scheduling and triggering the update of task statuses based on specified conditions.
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "statusupdater.scheduling.type", havingValue = "custom")
public class TaskStatusUpdateSchedulerImpl implements TaskStatusUpdateScheduler {

    private final TaskStatusUpdater statusUpdater;

    @Autowired
    public TaskStatusUpdateSchedulerImpl(TaskStatusUpdater statusUpdater) {
        this.statusUpdater = statusUpdater;
    }

    /**
     * Scheduled method to trigger the update of task statuses based on a cron expression.
     */
    @Scheduled(cron = "${statusupdater.scheduling.taskStatusUpdateCron}")
    public void updateTaskStatuses() {
        statusUpdater.updateStatusAll();
    }
}
