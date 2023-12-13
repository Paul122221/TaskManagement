package com.tasks.taskmanagement.infrastructure.persistence.jpa.mapper;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.infrastructure.persistence.jpa.entity.JpaTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The TaskMapperJpaImpl class is responsible for mapping domain-level Task objects to their corresponding JPA entity JpaTask objects.
 */
@Component
@Slf4j
public class TaskMapperJpaImpl implements TaskMapper{
    @Override
    public JpaTask mapToJpaTask(Task task) {
        if (task == null) {
            log.debug("Received a null task, returning null JpaTask.");
            return null;
        }
        JpaTask jpaTask;
        if (task instanceof JpaTask) {
            jpaTask = (JpaTask) task;
        } else {
            jpaTask = new JpaTask();
            jpaTask.setId(task.getId());
            jpaTask.setDescription(task.getDescription());
            jpaTask.setStatus(task.getStatus());
            jpaTask.setCreationDateTime(task.getCreationDateTime());
            jpaTask.setDueDateTime(task.getDueDateTime());
            jpaTask.setCompletionDateTime(task.getCompletionDateTime());
            log.debug("Mapped Task to JpaTask: {}", jpaTask);
        }
        return jpaTask;
    }

    @Override
    public Iterable<JpaTask> mapToJpaTaskList(Iterable<Task> tasks) {
        return StreamSupport.stream(tasks.spliterator(),false).map(
                this::mapToJpaTask
        ).collect(Collectors.toList());
    }
}
