package com.tasks.taskmanagement.application.spring.service.statusupdate;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.repository.TaskRepository;
import com.tasks.taskmanagement.domain.service.TaskService;
import com.tasks.taskmanagement.domain.strategy.StatusUpdateStrategy;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The TaskStatusUpdaterImpl class implements the TaskStatusUpdater interface.
 * It is responsible for updating the status of tasks based on a specific strategy.
 */
@Slf4j
@Service
public class TaskStatusUpdaterImpl implements TaskStatusUpdater {

    private final StatusUpdateStrategy statusUpdateStrategy;
    private final TaskService taskService;

    @Autowired
    public TaskStatusUpdaterImpl(StatusUpdateStrategy pastDueStatusUpdateStrategy, TaskService taskService) {
        this.statusUpdateStrategy = pastDueStatusUpdateStrategy;
        this.taskService = taskService;
    }

    /**
     * Update the status of a specific task based on the defined strategy.
     *
     * @param task The task to update the status for.
     */
    public void updateStatus(Task task) {
        if (statusUpdateStrategy.shouldUpdate(task)) {
            task.setStatus(TaskStatus.PAST_DUE);
            log.info("Task status updated to PAST_DUE for task with ID: {}", task.getId());
        }
    }

    /**
     * Update the status of all eligible tasks based on the defined strategy.
     * The method retrieves tasks that meet the criteria and updates their status in batches.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateStatusAll() {
        log.info("Starting task status update...");
        taskService.updateStatusForDueDateTimeAndOldStatus(TaskStatus.NOT_DONE,TaskStatus.PAST_DUE, LocalDateTime.now());
        log.info("Task status update completed.");
    }
}
