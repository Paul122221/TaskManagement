package com.tasks.taskmanagement.presentation.dto;

import com.tasks.taskmanagement.domain.valueobject.TaskStatus;

import java.time.LocalDateTime;

public interface TaskDto {

    Long getId();

    void setId(Long id);

    String getDescription();

    void setDescription(String description);

    LocalDateTime getCreationDateTime();

    void setCreationDateTime(LocalDateTime creationDateTime);

    LocalDateTime getDueDateTime();

    void setDueDateTime(LocalDateTime dueDateTime);

    LocalDateTime getCompletionDateTime();

    void setCompletionDateTime(LocalDateTime completionDateTime);

    TaskStatus getStatus();

    void setStatus(TaskStatus status);
}
