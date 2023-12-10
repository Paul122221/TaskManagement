package com.tasks.taskmanagement.application.spring.service.statusupdate;

import com.tasks.taskmanagement.domain.entity.Task;

public interface TaskStatusUpdater {
    void updateStatus(Task task);
    void updateStatusAll();
}
