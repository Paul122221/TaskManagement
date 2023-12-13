package com.tasks.taskmanagement.infrastructure.persistence.jpa.adapter;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.repository.TaskRepository;
import com.tasks.taskmanagement.domain.valueobject.*;
import com.tasks.taskmanagement.infrastructure.persistence.jpa.entity.JpaTask;
import com.tasks.taskmanagement.infrastructure.persistence.jpa.mapper.TaskMapper;
import com.tasks.taskmanagement.infrastructure.persistence.jpa.repository.JpaTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Adapter class that implements the TaskRepository interface for JPA-based task persistence.
 */
@Repository
public class JpaTaskRepositoryAdapter implements TaskRepository {

    private final JpaTaskRepository jpaTaskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public JpaTaskRepositoryAdapter(JpaTaskRepository jpaTaskRepository, TaskMapper taskMapper) {
        this.jpaTaskRepository = jpaTaskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public void delete(Task task) {
        jpaTaskRepository.deleteById(task.getId());
    }

    @Override
    public void deleteAll() {
        jpaTaskRepository.deleteAll();
    }

    @Override
    public IPage<Task> findAll(IPageable pageable) {
        Page<Task> result = jpaTaskRepository.findAll(PageRequest.of(pageable.getPage(), pageable.getSize()))
                .map(e -> (Task) e);
        return new PageImpl<>(result.getContent(),result.getTotalElements(),result.getNumber(),result.getSize());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaTaskRepository.findById(id).map(task -> task);
    }

    @Override
    public Task save(Task task) {
        return jpaTaskRepository.save(taskMapper.mapToJpaTask(task));
    }

    @Override
    public List<Task> saveAll(Iterable<Task> tasks) {
        Iterable<JpaTask> jpaTasks = taskMapper.mapToJpaTaskList(tasks);
        return new ArrayList<>(jpaTaskRepository.saveAll(jpaTasks));
    }

    @Override
    public Task newEntity() {
        return new JpaTask();
    }

    @Override
    public IPage<Task> findByStatus(IPageable pageable, TaskStatus status) {
        Page<Task> result = jpaTaskRepository
                .findByStatus(status, PageRequest.of(pageable.getPage(), pageable.getSize()))
                .map(e -> (Task) e);
        return new PageImpl<>(result.getContent(),result.getTotalElements(),result.getNumber(),result.getSize());
    }

    @Override
    public List<Task> findByStatusAndDueDateTimeBefore(TaskStatus status, LocalDateTime currentTime, int count) {
        Page<JpaTask> tasks = jpaTaskRepository
                .findByStatusAndDueDateTimeBefore(status, currentTime, PageRequest.of(0, count));
        return new ArrayList<>(tasks.stream().toList());
    }
}
