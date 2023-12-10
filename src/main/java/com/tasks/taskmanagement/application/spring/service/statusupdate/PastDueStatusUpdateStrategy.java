package com.tasks.taskmanagement.application.spring.service.statusupdate;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.strategy.StatusUpdateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PastDueStatusUpdateStrategy implements StatusUpdateStrategy {
    @Override
    public boolean shouldUpdate(Task task) {
        LocalDateTime dueDateTime = task.getDueDateTime();
        boolean isPastDue = dueDateTime != null && LocalDateTime.now().isAfter(dueDateTime);

        if (dueDateTime != null) {
            log.info("Checking if task {} is past its due date. Due date: {}, Current time: {}, Is past due: {}", task.getId(), dueDateTime, LocalDateTime.now(), isPastDue);
        } else {
            log.info("Task {} does not have a due date set.", task.getId());
        }

        return isPastDue;
    }
}
