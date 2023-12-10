package com.tasks.taskmanagement.infrastructure.persistence.jpa.mapper;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.infrastructure.persistence.jpa.entity.JpaTask;

public interface TaskMapper {

    JpaTask mapToJpaTask(Task task);

    Iterable<JpaTask> mapToJpaTaskList(Iterable<Task> tasks);

}
