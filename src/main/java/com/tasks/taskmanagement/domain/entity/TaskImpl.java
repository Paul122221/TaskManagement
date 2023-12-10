package com.tasks.taskmanagement.domain.entity;

import com.tasks.taskmanagement.domain.valueobject.TaskStatus;

import java.time.LocalDateTime;

public class TaskImpl implements Task {

    private Long id;

    private String description;

    private LocalDateTime creationDateTime;

    private LocalDateTime dueDateTime;

    private LocalDateTime completionDateTime;

    private TaskStatus status;

    public TaskImpl(String description, LocalDateTime creationDateTime) {
        this.description = description;
        this.creationDateTime = creationDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public LocalDateTime getCompletionDateTime() {
        return completionDateTime;
    }

    public void setCompletionDateTime(LocalDateTime completionDateTime) {
        this.completionDateTime = completionDateTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(TaskStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", dueDateTime=" + dueDateTime +
                ", completionDateTime=" + completionDateTime +
                ", status=" + status +
                '}';
    }
}
