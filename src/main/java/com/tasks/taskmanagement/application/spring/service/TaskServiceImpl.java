package com.tasks.taskmanagement.application.spring.service;

import com.tasks.taskmanagement.application.spring.service.statusupdate.TaskStatusUpdater;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.repository.TaskRepository;
import com.tasks.taskmanagement.domain.service.TaskService;
import com.tasks.taskmanagement.domain.strategy.StatusUpdateStrategy;
import com.tasks.taskmanagement.domain.validator.TaskValidator;
import com.tasks.taskmanagement.domain.valueobject.IPage;
import com.tasks.taskmanagement.domain.valueobject.IPageable;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The TaskServiceImpl class is the implementation of the TaskService interface.
 * It provides methods for managing tasks, including create, update, delete, and retrieve operations.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskValidator compositForDeleteTaskValidator;
    private final StatusUpdateStrategy statusUpdateStrategy;
    private final TaskValidator compositForSave;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           @Qualifier("compositForDeleteTaskValidator") TaskValidator compositForDeleteTaskValidator,
                           @Qualifier("compositForSave") TaskValidator compositForSave,
                           StatusUpdateStrategy statusUpdateStrategy) {
        this.taskRepository = taskRepository;
        this.compositForDeleteTaskValidator = compositForDeleteTaskValidator;
        this.statusUpdateStrategy = statusUpdateStrategy;
        this.compositForSave = compositForSave;
    }

    /**
     * Delete a task.
     *
     * @param task The task to be deleted.
     */
    @Override
    public void delete(Task task) {
        Task taskFromDB = this.findById(task.getId()).orElseThrow(
                () -> new EntityNotFoundException("Task not found with ID: " + task.getId())
        );
        compositForDeleteTaskValidator.validate(taskFromDB);
        taskRepository.delete(task);
    }

    /**
     * Delete all tasks.
     */
    @Override
    public void deleteAll() {
        taskRepository.deleteAll();
    }

    /**
     * Find all tasks with optional status update.
     *
     * @param pageable Pagination information.
     * @return A paginated list of tasks.
     */
    @Override
    public IPage<Task> findAll(IPageable pageable) {
        return taskRepository.findAll(pageable);
    }

    /**
     * Find a task by its ID with optional status update.
     *
     * @param id The ID of the task to retrieve.
     * @return The task with the given ID.
     */
    @Override
    @Transactional
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Save a task.
     *
     * @param task The task to be saved.
     * @return The saved task.
     */
    @Transactional
    @Override
    public Task save(Task task) {
        compositForSave.validate(task);
        return taskRepository.save(task);
    }

    /**
     * Save a list of tasks.
     *
     * @param tasks The list of tasks to be saved.
     * @return The saved tasks.
     */
    @Override
    public List<Task> saveAll(Iterable<Task> tasks) {
        return taskRepository.saveAll(tasks);
    }

    /**
     * Create a new task entity.
     *
     * @return A new task entity.
     */
    @Override
    public Task newEntity() {
        return taskRepository.newEntity();
    }

    /**
     * Find tasks by status with optional status update.
     *
     * @param pageable Pagination information.
     * @param status   Status filter.
     * @return A paginated list of tasks filtered by status.
     */
    @Override
    public IPage<Task> findByStatus(IPageable pageable, TaskStatus status) {
        return taskRepository.findByStatus(pageable, status);
    }

    /**
     * Find tasks by status and due date-time before a given time.
     *
     * @param status       Status filter.
     * @param currentTime  The current date-time.
     * @param count        The maximum number of tasks to retrieve.
     * @return A list of tasks filtered by status and due date-time.
     */
    @Override
    public List<Task> findByStatusAndDueDateTimeBefore(TaskStatus status, LocalDateTime currentTime, int count) {
        return taskRepository.findByStatusAndDueDateTimeBefore(status, currentTime, count);
    }


    /**
     * Updates the status of tasks that meet the specified criteria.
     *
     * @param oldStatus        The old status of tasks to be updated.
     * @param newStatus        The new status to set for the tasks.
     * @param currentTimestamp The timestamp used for comparison with the dueDateTime field of tasks.
     */
    @Override
    public void updateStatusForDueDateTimeAndOldStatus(TaskStatus oldStatus, TaskStatus newStatus, LocalDateTime currentTimestamp) {
        taskRepository.updateStatusForDueDateTimeAndOldStatus(oldStatus,newStatus,currentTimestamp);
    }
}
