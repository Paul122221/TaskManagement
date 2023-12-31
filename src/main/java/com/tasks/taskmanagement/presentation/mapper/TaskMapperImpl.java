package com.tasks.taskmanagement.presentation.mapper;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.service.TaskService;
import com.tasks.taskmanagement.presentation.dto.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of the TaskMapper interface for mapping between Task entities and TaskDto objects.
 */
@Component
@Slf4j
public class TaskMapperImpl implements TaskMapper {

    private final TaskService taskService;

    @Autowired
    public TaskMapperImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a Task entity from a TaskDto object.
     *
     * @param taskPatchRequest The TaskDto object to create the Task entity from.
     * @return The created Task entity.
     */
    public Task createFrom(TaskDto taskPatchRequest) {
        Task task = taskService.newEntity();
        patchFromDto(task, taskPatchRequest);
        log.info("Created Task from TaskDto: {}", task);
        return task;
    }

    /**
     * Updates a Task entity based on the data in a TaskDto object.
     *
     * @param task             The Task entity to update.
     * @param taskPatchRequest The TaskDto object containing the updated data.
     */
    public void updateFromDto(Task task, TaskDto taskPatchRequest) {
        task.setDescription(taskPatchRequest.getDescription());
        task.setCreationDateTime(taskPatchRequest.getCreationDateTime());
        task.setDueDateTime(taskPatchRequest.getDueDateTime());
        task.setCompletionDateTime(taskPatchRequest.getCompletionDateTime());
        task.setStatus(taskPatchRequest.getStatus());
        log.info("Updated Task from TaskDto: {}", task);
    }

    /**
     * Patches a Task entity based on the data in a TaskDto object.
     *
     * @param task             The Task entity to patch.
     * @param taskPatchRequest The TaskDto object containing the data for patching.
     */
    public void patchFromDto(Task task, TaskDto taskPatchRequest) {
        if (taskPatchRequest.getId() != null) {
            task.setId(taskPatchRequest.getId());
        }
        if (taskPatchRequest.getDescription() != null) {
            task.setDescription(taskPatchRequest.getDescription());
        }
        if (taskPatchRequest.getCreationDateTime() != null) {
            task.setCreationDateTime(taskPatchRequest.getCreationDateTime());
        }
        if (taskPatchRequest.getDueDateTime() != null) {
            task.setDueDateTime(taskPatchRequest.getDueDateTime());
        }
        if (taskPatchRequest.getCompletionDateTime() != null) {
            task.setCompletionDateTime(taskPatchRequest.getCompletionDateTime());
        }
        if (taskPatchRequest.getStatus() != null) {
            task.setStatus(taskPatchRequest.getStatus());
        }
        log.info("Patched Task from TaskDto: {}", task);
    }
}
