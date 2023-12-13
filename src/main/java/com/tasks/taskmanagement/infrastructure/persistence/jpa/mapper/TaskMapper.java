package com.tasks.taskmanagement.infrastructure.persistence.jpa.mapper;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.infrastructure.persistence.jpa.entity.JpaTask;

/**
 * The TaskMapper interface defines methods for mapping domain-level Task objects to their corresponding JPA entity JpaTask objects.
 */
public interface TaskMapper {

    /**
     * Maps a single domain-level Task object to a JPA entity JpaTask object.
     *
     * @param task The Task object to be mapped.
     * @return A JpaTask object representing the mapped Task.
     */
    JpaTask mapToJpaTask(Task task);

    /**
     * Maps an iterable of domain-level Task objects to an iterable of JPA entity JpaTask objects.
     *
     * @param tasks An iterable of Task objects to be mapped.
     * @return An iterable of JpaTask objects representing the mapped Tasks.
     */
    Iterable<JpaTask> mapToJpaTaskList(Iterable<Task> tasks);
}
