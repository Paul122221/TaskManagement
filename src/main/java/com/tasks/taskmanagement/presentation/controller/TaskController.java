package com.tasks.taskmanagement.presentation.controller;

import com.tasks.taskmanagement.application.spring.service.statusupdate.TaskStatusUpdater;
import com.tasks.taskmanagement.application.spring.validators.TaskValidator;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.service.TaskService;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import com.tasks.taskmanagement.presentation.dto.TaskDtoImpl;
import com.tasks.taskmanagement.presentation.exception.RequestNotModifiableException;
import com.tasks.taskmanagement.presentation.mapper.TaskMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper, TaskStatusUpdater taskStatusUpdater) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.taskStatusUpdater = taskStatusUpdater;
    }

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(name = "status", required = false) TaskStatus status) {
        log.info("Getting all tasks");
        List<Task> tasks;

        if (status != null) {
            tasks = taskService.findByStatus(status);
        } else {
            tasks = taskService.findAll();
        }

        return ResponseEntity.ok(tasks);
    }

    // Get a task by its ID
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        log.info("Getting task by ID: {}", taskId);
        Task task = taskService.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with ID: " + taskId)
        );
        taskStatusUpdater.updateStatus(task);
        return ResponseEntity.ok(task);
    }

    // Create a new task
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskDtoImpl taskPatchRequest) {
        log.info("Creating new task");
        Task task = taskService.save(taskMapper.createFrom(taskPatchRequest));
        return ResponseEntity.ok(task);
    }


    @Autowired
    TaskValidator taskValidator;

    // Update a task by its ID
    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable Long taskId, @RequestBody @Valid TaskDtoImpl taskDto) {
        log.info("Updating task by ID: {}", taskId);
        Task task = taskService.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with ID: " + taskId)
        );
        taskStatusUpdater.updateStatus(task);
        if (!taskValidator.validate(task)) {
            throw new RequestNotModifiableException("The task cannot be changed");
        }
        taskMapper.updateFromDto(task, taskDto);

        taskService.save(task);
        return ResponseEntity.ok("Task updated successfully");
    }


    // Delete a task by its ID
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        log.info("Deleting task by ID: {}", taskId);
        Task task = taskService.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with ID: " + taskId)
        );
        taskService.delete(task);
        return ResponseEntity.ok("Task deleted successfully");
    }

    // Delete all tasks
    @DeleteMapping("/")
    public void deleteAllTasks() {
        log.info("Deleting all tasks");
        taskService.deleteAll();
    }

    // Partially update a task by its ID using a patch request
    @PatchMapping("/{taskId}")
    public ResponseEntity<Task> updateTaskPartially(@PathVariable Long taskId, @RequestBody TaskDtoImpl taskDto) {
        log.info("Partially updating task by ID: {}", taskId);
        Task task = taskService.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with ID: " + taskId)
        );
        if (!taskValidator.validate(task)) {
            throw new RequestNotModifiableException("The task cannot be changed");
        }
        taskMapper.patchFromDto(task, taskDto);
        taskService.save(task);
        return ResponseEntity.ok(task);
    }

}