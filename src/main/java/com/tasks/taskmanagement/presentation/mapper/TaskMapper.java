package com.tasks.taskmanagement.presentation.mapper;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.presentation.dto.TaskDto;

public interface TaskMapper {

    Task createFrom(TaskDto taskPatchRequest);

    void updateFromDto(Task task, TaskDto taskPatchRequest);

    void patchFromDto(Task task, TaskDto taskPatchRequest);
}
