package com.tasks.taskmanagement.presentation.mapper;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.presentation.dto.TaskDto;

/**
 * An interface for mapping between Task entities and TaskDto objects.
 */
public interface TaskMapper {

    /**
     * Creates a Task entity from a TaskDto object.
     *
     * @param taskPatchRequest The TaskDto object to create the Task entity from.
     * @return The created Task entity.
     */
    Task createFrom(TaskDto taskPatchRequest);

    /**
     * Updates a Task entity based on the data in a TaskDto object.
     *
     * @param task             The Task entity to update.
     * @param taskPatchRequest The TaskDto object containing the updated data.
     */
    void updateFromDto(Task task, TaskDto taskPatchRequest);

    /**
     * Patches a Task entity based on the data in a TaskDto object.
     *
     * @param task             The Task entity to patch.
     * @param taskPatchRequest The TaskDto object containing the data for patching.
     */
    void patchFromDto(Task task, TaskDto taskPatchRequest);
}
