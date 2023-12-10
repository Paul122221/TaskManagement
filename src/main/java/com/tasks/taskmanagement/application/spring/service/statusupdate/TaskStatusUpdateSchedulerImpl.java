package com.tasks.taskmanagement.application.spring.service.statusupdate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "statusupdater.scheduling.type", havingValue = "custom")
public class TaskStatusUpdateSchedulerImpl implements TaskStatusUpdateScheduler{

    private final TaskStatusUpdater statusUpdater;

    @Autowired
    public TaskStatusUpdateSchedulerImpl(TaskStatusUpdater statusUpdater) {
        this.statusUpdater = statusUpdater;
    }

    @Scheduled(cron = "${statusupdater.scheduling.taskStatusUpdateCron}")
    public void updateTaskStatuses() {
        log.info("Starting task status update...");
        statusUpdater.updateStatusAll();
        log.info("Task status update completed.");
    }
}
