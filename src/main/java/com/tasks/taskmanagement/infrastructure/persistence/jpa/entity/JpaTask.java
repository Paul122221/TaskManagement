package com.tasks.taskmanagement.infrastructure.persistence.jpa.entity;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * JPA entity class representing a Task in the database.
 */
@Entity
@Table(name = "tasks")
public class JpaTask implements Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "creationDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDateTime;

    @Column(name = "dueDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dueDateTime;

    @Column(name = "completionDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime completionDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    public JpaTask() {
        this.description = "Undefined";
        this.creationDateTime = LocalDateTime.now();
    }

    public JpaTask(String description, LocalDateTime creationDateTime, TaskStatus taskStatus) {
        this.description = description;
        this.creationDateTime = creationDateTime;
        this.status = taskStatus;
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