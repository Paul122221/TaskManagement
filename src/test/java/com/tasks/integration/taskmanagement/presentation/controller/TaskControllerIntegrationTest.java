package com.tasks.integration.taskmanagement.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.entity.TaskImpl;
import com.tasks.taskmanagement.domain.service.TaskService;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import com.tasks.taskmanagement.presentation.dto.TaskDtoImpl;
import com.tasks.taskmanagement.presentation.mapper.TaskMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskService taskService;


    @BeforeEach
    public void setUp() {
        // Clear the database or prepare data as needed
        taskService.deleteAll();
    }

    @AfterEach
    public void setAfterEach() {
        // Clear the database or prepare data as needed
        taskService.deleteAll();
    }

    @Test
    public void testCreateTask() throws JsonProcessingException {
        // Create a TaskDtoImpl object with data for task creation
        TaskDtoImpl taskDto = new TaskDtoImpl();
        taskDto.setDescription("Test Task");
        taskDto.setStatus(TaskStatus.NOT_DONE);

        // Send an HTTP POST request to create the task
        ResponseEntity<TaskImpl> responseEntity = restTemplate.postForEntity("/api/tasks", new HttpEntity<>(taskDto), TaskImpl.class);

        // Check that the request returns a 200 OK status
        assertEquals(200, responseEntity.getStatusCodeValue());

        Task createdTask = responseEntity.getBody();
        assertNotNull(createdTask);
        Task task = taskService.findById(createdTask.getId()).orElse(null);
        assertNotNull(task);
        assertEquals("Test Task", task.getDescription());
    }


    @Test
    public void testGetTaskById() {
        // Create a task and retrieve its ID
        TaskDtoImpl taskDto = new TaskDtoImpl();
        taskDto.setDescription("Test Task");
        taskDto.setStatus(TaskStatus.NOT_DONE);
        ResponseEntity<TaskImpl> createResponseEntity = restTemplate.postForEntity("/api/tasks", new HttpEntity<>(taskDto), TaskImpl.class);
        TaskImpl createdTask = createResponseEntity.getBody();
        assertNotNull(createdTask);
        Long taskId = createdTask.getId();

        // Send an HTTP GET request to retrieve the task by ID
        ResponseEntity<TaskImpl> responseEntity = restTemplate.getForEntity("/api/tasks/" + taskId, TaskImpl.class);

        // Check that the request returns a 200 OK status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Check that the response contains the expected task
        Task retrievedTask = responseEntity.getBody();
        assertNotNull(retrievedTask);
        assertEquals("Test Task", retrievedTask.getDescription());
    }
}
