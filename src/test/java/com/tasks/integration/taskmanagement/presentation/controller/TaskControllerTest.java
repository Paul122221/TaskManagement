package com.tasks.integration.taskmanagement.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tasks.taskmanagement.application.spring.exception.TaskValidationException;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.entity.TaskImpl;
import com.tasks.taskmanagement.domain.repository.TaskRepository;
import com.tasks.taskmanagement.domain.service.TaskService;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import com.tasks.taskmanagement.presentation.dto.TaskDtoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;


    @BeforeEach
    public void setUp() {
        // Clear the database or prepare data as needed
        taskRepository.deleteAll();
    }


    @AfterEach
    public void setAfterEach() {
        // Clear the database or prepare data as needed
        taskRepository.deleteAll();
    }


    /**
     * Tests the behavior when trying to save a task with status NOT_DONE but with an expired due date.
     */
    @Test
    public void testNotDoneButWithExpieredTime(){
        // Create a TaskDtoImpl object with Not Done But With Expiered Time
        TaskDtoImpl taskDto = new TaskDtoImpl();
        taskDto.setDescription("Test Task");
        taskDto.setStatus(TaskStatus.NOT_DONE);
        taskDto.setDueDateTime(LocalDateTime.now().minusMinutes(1));

        // Attempt to save invalid Task
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/tasks", new HttpEntity<>(taskDto), String.class);

        // Should return BAD_REQUEST
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        assertEquals(statusCode,HttpStatus.BAD_REQUEST);
    }


    /**
     * Tests the behavior when attempting to save a task with the PAST_DUE status but with a due date in the future,
     * which is considered invalid.
     */
    @Test
    public void pastDueButTimeIsNotExpiredTime() {
        // Prepare a TaskDtoImpl object with the PAST_DUE status and a future due date
        TaskDtoImpl taskDto = new TaskDtoImpl();
        taskDto.setDescription("Test Task");
        taskDto.setStatus(TaskStatus.PAST_DUE);
        taskDto.setDueDateTime(LocalDateTime.now().plusMinutes(1));

        // Attempt to save the invalid TaskDto
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/tasks", new HttpEntity<>(taskDto), String.class);

        // Ensure that the response status code is BAD_REQUEST
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    }


    /**
     * Tests the auto-update of task status when fetching a task with an expired due date.
     */
    @Test
    public void checkStatusAutoUpdate() {
        // Create a test task with a due date in the past
        Task task = new TaskImpl("test", LocalDateTime.now());
        task.setId(1L);
        task.setStatus(TaskStatus.NOT_DONE);
        task.setDueDateTime(LocalDateTime.now().minusMinutes(1));

        // Save the task to the repository
        taskRepository.save(task);

        // Fetch the task using REST API
        ResponseEntity<TaskImpl> responseEntity = restTemplate.getForEntity("/api/tasks/1", TaskImpl.class);

        // Ensure that the response status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Get the updated status of the fetched task
        TaskStatus status = responseEntity.getBody().getStatus();

        // Verify that the task status has been automatically updated to PAST_DUE
        assertEquals(TaskStatus.PAST_DUE, status);
    }


    /**
     * Tests the creation of a new task using an HTTP POST request.
     * It verifies that the request returns a 200 OK status and that the created task
     * has the expected description.
     */
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

        // Retrieve the created task by ID
        Task task = taskService.findById(createdTask.getId()).orElse(null);
        assertNotNull(task);

        // Check that the created task has the expected description
        assertEquals("Test Task", task.getDescription());
    }


    /**
     * Tests the retrieval of a task by its ID using an HTTP GET request.
     * It verifies that the request returns a 200 OK status and that the retrieved task
     * matches the expected description.
     */
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

        // Check that the retrieved task has the expected description
        assertEquals("Test Task", retrievedTask.getDescription());
    }
}