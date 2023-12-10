package com.tasks.taskmanagement.domain.entity;

import com.tasks.taskmanagement.domain.valueobject.TaskStatus;

import java.time.LocalDateTime;

public interface Task {

    Long getId();

    void setId(Long id);

    String getDescription();

    void setDescription(String description);

    TaskStatus getStatus();

    void setStatus(TaskStatus status);

    LocalDateTime getCreationDateTime();

    void setCreationDateTime(LocalDateTime creationDateTime);

    LocalDateTime getDueDateTime();

    void setDueDateTime(LocalDateTime dueDateTime);

    LocalDateTime getCompletionDateTime();

    void setCompletionDateTime(LocalDateTime completionDateTime);

}
