package com.tasks.unit.taskmanagement.application.spring.service.statusupdate;

import com.tasks.taskmanagement.application.spring.service.statusupdate.TaskStatusUpdaterImpl;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.entity.TaskImpl;
import com.tasks.taskmanagement.domain.repository.TaskRepository;
import com.tasks.taskmanagement.domain.strategy.StatusUpdateStrategy;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskStatusUpdaterImplTest {

    @Autowired
    private TaskStatusUpdaterImpl taskStatusUpdater;

    @Mock
    private StatusUpdateStrategy statusUpdateStrategy;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        taskStatusUpdater = new TaskStatusUpdaterImpl(statusUpdateStrategy, taskRepository);
    }

    @Test
    void updateStatus_ShouldUpdateStatusToPastDue_WhenShouldUpdateReturnsTrue() {
        Task task = new TaskImpl("Undefined", LocalDateTime.now());
        task.setStatus(TaskStatus.NOT_DONE);
        when(statusUpdateStrategy.shouldUpdate(task)).thenReturn(true);
        taskStatusUpdater.updateStatus(task);

        verify(statusUpdateStrategy).shouldUpdate(task);
        assertEquals(TaskStatus.PAST_DUE, task.getStatus());
    }

    @Test
    void updateStatus_ShouldNotUpdateStatus_WhenShouldUpdateReturnsFalse() {
        Task task = new TaskImpl("Undefined", LocalDateTime.now());
        task.setStatus(TaskStatus.NOT_DONE);
        when(statusUpdateStrategy.shouldUpdate(task)).thenReturn(false);
        taskStatusUpdater.updateStatus(task);

        verify(statusUpdateStrategy).shouldUpdate(task);
        verify(taskRepository, never()).save(task);
        assertEquals(TaskStatus.NOT_DONE, task.getStatus());
    }

    @Test
    void updateStatusAll_ShouldUpdateStatusForTasksToPastDue_WhenTasksAreNotDoneAndShouldUpdateReturnsTrue() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new TaskImpl("Undefined", LocalDateTime.now().minusSeconds(5));
        task1.setStatus(TaskStatus.NOT_DONE);
        task1.setDueDateTime(LocalDateTime.now().minusSeconds(3));
        tasks.add(task1);

        Task task2 = new TaskImpl("Undefined", LocalDateTime.now().minusSeconds(5));
        task2.setStatus(TaskStatus.NOT_DONE);
        task2.setDueDateTime(LocalDateTime.now().minusSeconds(3));
        tasks.add(task2);

        when(taskRepository.findByStatusAndDueDateTimeBefore(any(TaskStatus.class),any(LocalDateTime.class),anyInt())).thenReturn(tasks);
        when(statusUpdateStrategy.shouldUpdate(any(Task.class))).thenReturn(true);

        taskStatusUpdater.updateStatusAll();

        verify(taskRepository).findByStatusAndDueDateTimeBefore(any(TaskStatus.class),any(LocalDateTime.class),anyInt());
        assertEquals(TaskStatus.PAST_DUE, task1.getStatus());
        assertEquals(TaskStatus.PAST_DUE, task2.getStatus());
    }

    @Test
    void updateStatusAll_ShouldNotUpdateStatus_WhenTasksAreNotDoneAndShouldUpdateReturnsFalse() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new TaskImpl("Undefined", LocalDateTime.now());
        task1.setStatus(TaskStatus.NOT_DONE);
        tasks.add(task1);
        Task task2 = new TaskImpl("Undefined", LocalDateTime.now());
        task2.setStatus(TaskStatus.NOT_DONE);
        tasks.add(task2);

        when(taskRepository.findByStatusAndDueDateTimeBefore(any(TaskStatus.class),any(LocalDateTime.class),anyInt())).thenReturn(tasks);
        when(statusUpdateStrategy.shouldUpdate(any(Task.class))).thenReturn(false);

        taskStatusUpdater.updateStatusAll();

        verify(taskRepository).findByStatusAndDueDateTimeBefore(any(TaskStatus.class),any(LocalDateTime.class),anyInt());
        assertEquals(TaskStatus.NOT_DONE, task1.getStatus());
        assertEquals(TaskStatus.NOT_DONE, task2.getStatus());
    }

    @Test
    void updateStatusAll_ShouldNotUpdateStatus_WhenNoTasksAreNotDone() {
        List<Task> tasks = new ArrayList<>();

        when(taskRepository.findByStatusAndDueDateTimeBefore(any(TaskStatus.class),any(LocalDateTime.class),anyInt())).thenReturn(tasks);
        when(statusUpdateStrategy.shouldUpdate(any(Task.class))).thenReturn(true);

        taskStatusUpdater.updateStatusAll();

        verify(taskRepository).findByStatusAndDueDateTimeBefore(any(TaskStatus.class),any(LocalDateTime.class),anyInt());
        verify(taskRepository, never()).saveAll(tasks);
    }
}
