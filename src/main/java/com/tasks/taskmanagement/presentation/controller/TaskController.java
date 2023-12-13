package com.tasks.taskmanagement.presentation.controller;

import com.tasks.taskmanagement.application.spring.service.statusupdate.TaskStatusUpdater;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.service.TaskService;
import com.tasks.taskmanagement.domain.validator.TaskValidator;
import com.tasks.taskmanagement.domain.valueobject.IPage;
import com.tasks.taskmanagement.domain.valueobject.IPageable;
import com.tasks.taskmanagement.domain.valueobject.PageableImpl;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import com.tasks.taskmanagement.presentation.dto.TaskDtoImpl;
import com.tasks.taskmanagement.presentation.mapper.TaskMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The TaskController class represents the RESTful API controller for managing tasks.
 * It provides endpoints for creating, updating, retrieving, and deleting tasks, as well as
 * retrieving lists of tasks.
 */
@RestController
@RequestMapping("/api/tasks")
@Validated
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskStatusUpdater taskStatusUpdater;
    private final TaskValidator compositNotDoneNotPassDue;

    @Autowired
    public TaskController(
            TaskService taskService,
            TaskMapper taskMapper,
            TaskStatusUpdater taskStatusUpdater,
            @Qualifier("compositNotDoneNotPassDue") TaskValidator compositNotDoneNotPassDue
    ) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.taskStatusUpdater = taskStatusUpdater;
        this.compositNotDoneNotPassDue = compositNotDoneNotPassDue;
    }

    /**
     * Get all tasks with optional filtering by status.
     *
     * @param status Status filter (optional)
     * @param page   Page number (default is 0)
     * @param size   Page size (default is 10)
     * @return A paginated list of tasks.
     */
    @GetMapping("")
    public ResponseEntity<IPage<Task>> getAllTasks(@RequestParam(name = "status", required = false) TaskStatus status,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Getting all tasks");
        IPage<Task> tasks;
        IPageable pageable = new PageableImpl(page, size);

        if (status != null) {
            tasks = taskService.findByStatus(pageable, status);
        } else {
            tasks = taskService.findAll(pageable);
        }

        return ResponseEntity.ok(tasks);
    }

    /**
     * Get a task by its ID.
     *
     * @param taskId The ID of the task to retrieve.
     * @return The task with the given ID.
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        log.info("Getting task by ID: {}", taskId);
        Task task = taskService.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with ID: " + taskId)
        );
        taskStatusUpdater.updateStatus(task);
        return ResponseEntity.ok(task);
    }

    /**
     * Delete a task by its ID.
     *
     * @param taskId The ID of the task to delete.
     * @return A success message indicating that the task was deleted.
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        log.info("Deleting task by ID: {}", taskId);
        Task task = taskService.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with ID: " + taskId)
        );
        taskService.delete(task);
        return ResponseEntity.ok("Task deleted successfully");
    }

    /**
     * Delete all tasks.
     */
    @DeleteMapping("")
    public void deleteAllTasks() {
        log.info("Deleting all tasks");
        taskService.deleteAll();
    }

    /**
     * Partially update a task by its ID using a patch request.
     *
     * @param taskId   The ID of the task to update.
     * @param taskDto  The partial task data to apply.
     * @return The updated task.
     */
    @PatchMapping("/{taskId}")
    public ResponseEntity<Task> updateTaskPartially(@PathVariable Long taskId, @RequestBody TaskDtoImpl taskDto) {
        log.info("Partially updating task by ID: {}", taskId);
        Task task = taskService.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with ID: " + taskId)
        );
        compositNotDoneNotPassDue.validate(task);
        taskMapper.patchFromDto(task, taskDto);
        taskService.save(task);
        return ResponseEntity.ok(task);
    }

    /**
     * Update a task by its ID.
     *
     * @param taskId   The ID of the task to update.
     * @param taskDto  The updated task data.
     * @return A success message indicating that the task was updated.
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable Long taskId, @RequestBody @Valid TaskDtoImpl taskDto) {
        log.info("Updating task by ID: {}", taskId);
        Task task = taskService.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with ID: " + taskId)
        );
        compositNotDoneNotPassDue.validate(task);
        taskMapper.updateFromDto(task, taskDto);
        taskService.save(task);
        return ResponseEntity.ok("Task updated successfully");
    }

    /**
     * Create a new task.
     *
     * @param taskPatchRequest The task data to create a new task.
     * @return The newly created task.
     */
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskDtoImpl taskPatchRequest) {
        log.info("Creating new task");
        Task task = taskService.save(taskMapper.createFrom(taskPatchRequest));
        return ResponseEntity.ok(task);
    }
}
