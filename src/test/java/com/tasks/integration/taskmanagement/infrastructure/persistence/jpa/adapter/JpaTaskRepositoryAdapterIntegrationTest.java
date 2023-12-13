package com.tasks.integration.taskmanagement.infrastructure.persistence.jpa.adapter;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.entity.TaskImpl;
import com.tasks.taskmanagement.domain.repository.TaskRepository;
import com.tasks.taskmanagement.domain.valueobject.IPage;
import com.tasks.taskmanagement.domain.valueobject.PageableImpl;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JpaTaskRepositoryAdapterIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        // Clear all data before each test
        taskRepository.deleteAll();
    }


    @AfterEach
    public void setAfterEach() {
        // Clear the database or prepare data as needed
        taskRepository.deleteAll();
    }

    @Test
    void save_ShouldSaveTaskToDatabase() {
        // Create a task instance
        Task task = taskRepository.newEntity();
        task.setDescription("Test Task");
        task.setStatus(TaskStatus.NOT_DONE);

        // Save the task to the repository
        taskRepository.save(task);

        // Retrieve the task from the database
        Optional<Task> savedTask = taskRepository.findById(task.getId());

        // Verify that the task was successfully saved
        assertTrue(savedTask.isPresent());
        assertEquals("Test Task", savedTask.get().getDescription());
    }

    @Test
    void findByStatus_ShouldReturnTasksWithMatchingStatus() {
        // Create tasks with different statuses
        Task task1 = new TaskImpl("Task 1", LocalDateTime.now());
        task1.setStatus(TaskStatus.NOT_DONE);

        Task task2 = new TaskImpl("Task 2", LocalDateTime.now());
        task2.setStatus(TaskStatus.DONE);

        Task task3 = new TaskImpl("Task 3", LocalDateTime.now());
        task3.setStatus(TaskStatus.NOT_DONE);

        // Save the tasks in the repository
        taskRepository.saveAll(List.of(task1, task2, task3));

        // Find tasks with status "NOT_DONE"
        IPage<Task> notDoneTasks = taskRepository.findByStatus(new PageableImpl(0,10),TaskStatus.NOT_DONE);

        // Verify that tasks with the expected status were returned
        assertEquals(2, notDoneTasks.getContent().size());
        assertTrue(notDoneTasks.getContent().stream().allMatch(task -> task.getStatus() == TaskStatus.NOT_DONE));
    }
}
