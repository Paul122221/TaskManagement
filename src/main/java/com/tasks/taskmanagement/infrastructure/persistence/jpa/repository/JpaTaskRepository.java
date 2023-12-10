package com.tasks.taskmanagement.infrastructure.persistence.jpa.repository;

import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import com.tasks.taskmanagement.infrastructure.persistence.jpa.entity.JpaTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface JpaTaskRepository extends JpaRepository<JpaTask, Long> {

    List<JpaTask> findByStatus(TaskStatus status);

    Page<JpaTask> findByStatusAndDueDateTimeBefore(TaskStatus status, LocalDateTime currentTime, Pageable pageable);

}
