package com.tasks.taskmanagement.infrastructure.persistence.jpa.repository;

import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import com.tasks.taskmanagement.infrastructure.persistence.jpa.entity.JpaTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


/**
 * The JpaTaskRepository interface extends JpaRepository for JpaTask entities and provides custom query methods for retrieving tasks based on status and due date-time.
 */
@Repository
public interface JpaTaskRepository extends JpaRepository<JpaTask, Long> {

    /**
     * Retrieves a page of tasks with the specified status.
     *
     * @param status   The status of the tasks to retrieve.
     * @param pageable Pageable object to specify the page number and size.
     * @return A page of tasks with the specified status.
     */
    Page<JpaTask> findByStatus(TaskStatus status, Pageable pageable);

    /**
     * Retrieves a page of tasks with the specified status and due date-time before the current time.
     *
     * @param status       The status of the tasks to retrieve.
     * @param currentTime  The current time used to filter tasks with due date-time before it.
     * @param pageable     Pageable object to specify the page number and size.
     * @return A page of tasks with the specified status and due date-time before the current time.
     */
    Page<JpaTask> findByStatusAndDueDateTimeBefore(TaskStatus status, LocalDateTime currentTime, Pageable pageable);
}
