package com.tasks.taskmanagement.domain.service;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    void delete(Task task);

    void deleteAll();

    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task save(Task task);

    List<Task> saveAll(Iterable<Task> tasks);

    Task newEntity();

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByStatusAndDueDateTimeBefore(TaskStatus status, LocalDateTime currentTime, int count);
}
