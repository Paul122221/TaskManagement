package com.tasks.taskmanagement.application.spring.service.statusupdate;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.strategy.StatusUpdateStrategy;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * The PastDueStatusUpdateStrategy class implements the StatusUpdateStrategy interface.
 * It defines a strategy for checking if a task is past its due date and should have its status updated.
 */
@Service
@Slf4j
public class PastDueStatusUpdateStrategy implements StatusUpdateStrategy {

    /**
     * Determines whether a task should have its status updated based on whether it is past its due date.
     *
     * @param task The task to be checked.
     * @return True if the task is past due and its status should be updated, false otherwise.
     */
    @Override
    public boolean shouldUpdate(Task task) {
        LocalDateTime dueDateTime = task.getDueDateTime();
        boolean isPastDue = dueDateTime != null
                && LocalDateTime.now().isAfter(dueDateTime)
                && task.getStatus() == TaskStatus.NOT_DONE;

        if (dueDateTime != null) {
            log.info("Checking if task {} is past its due date. Due date: {}, Current time: {}, Is past due: {}",
                    task.getId(), dueDateTime, LocalDateTime.now(), isPastDue);
        } else {
            log.info("Task {} does not have a due date set.", task.getId());
        }

        return isPastDue;
    }
}
