package com.tasks.taskmanagement.domain.service;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.valueobject.IPage;
import com.tasks.taskmanagement.domain.valueobject.IPageable;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The TaskService interface defines methods for managing tasks in a service layer.
 */
public interface TaskService {

    /**
     * Delete a task.
     *
     * @param task The task to be deleted.
     */
    void delete(Task task);

    /**
     * Delete all tasks.
     */
    void deleteAll();

    /**
     * Find all tasks with pagination.
     *
     * @param pageable The pagination information.
     * @return A paginated list of tasks.
     */
    IPage<Task> findAll(IPageable pageable);

    /**
     * Find a task by its ID.
     *
     * @param id The ID of the task to find.
     * @return An optional containing the found task, or empty if not found.
     */
    Optional<Task> findById(Long id);

    /**
     * Save a task.
     *
     * @param task The task to be saved.
     * @return The saved task.
     */
    Task save(Task task);

    /**
     * Save a list of tasks.
     *
     * @param tasks The list of tasks to be saved.
     * @return The saved list of tasks.
     */
    List<Task> saveAll(Iterable<Task> tasks);

    /**
     * Create a new task entity instance.
     *
     * @return A new task entity instance.
     */
    Task newEntity();

    /**
     * Find tasks by their status with pagination.
     *
     * @param pageable The pagination information.
     * @param status   The status to filter tasks by.
     * @return A paginated list of tasks with the specified status.
     */
    IPage<Task> findByStatus(IPageable pageable, TaskStatus status);

    /**
     * Find tasks by their status and due date/time before a specified time.
     *
     * @param status       The status to filter tasks by.
     * @param currentTime  The current time used for comparison.
     * @param count        The maximum number of tasks to retrieve.
     * @return A list of tasks with the specified status and due date/time before the current time.
     */
    List<Task> findByStatusAndDueDateTimeBefore(TaskStatus status, LocalDateTime currentTime, int count);

    /**
     * Updates the status of tasks that meet the specified criteria.
     *
     * @param oldStatus        The old status of tasks to be updated.
     * @param newStatus        The new status to set for the tasks.
     * @param currentTimestamp The timestamp used for comparison with the dueDateTime field of tasks.
     */
    void updateStatusForDueDateTimeAndOldStatus(TaskStatus oldStatus,
                                               TaskStatus newStatus,
                                               LocalDateTime currentTimestamp);
}
