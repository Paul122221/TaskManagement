package com.tasks.unit.taskmanagement.application.spring.service.statusupdate;

import com.tasks.taskmanagement.application.spring.service.statusupdate.PastDueStatusUpdateStrategy;
import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PastDueStatusUpdateStrategyTest {

    private PastDueStatusUpdateStrategy strategy;

    @Autowired
    public PastDueStatusUpdateStrategyTest(PastDueStatusUpdateStrategy strategy) {
        this.strategy = strategy;
    }

    @Mock
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        strategy = new PastDueStatusUpdateStrategy();
    }

    @Test
    void shouldUpdate_ReturnsTrue_WhenDueDateTimeIsPastDue() {
        LocalDateTime dueDateTime = LocalDateTime.now().minusHours(1); // Past due
        when(task.getDueDateTime()).thenReturn(dueDateTime);
        when(task.getStatus()).thenReturn(TaskStatus.NOT_DONE);

        boolean result = strategy.shouldUpdate(task);

        assertTrue(result);
    }

    @Test
    void shouldUpdate_ReturnsFalse_WhenDueDateTimeIsNull() {
        when(task.getDueDateTime()).thenReturn(null);

        boolean result = strategy.shouldUpdate(task);

        assertFalse(result);
    }

    @Test
    void shouldUpdate_ReturnsFalse_WhenDueDateTimeIsNotPastDue() {
        LocalDateTime dueDateTime = LocalDateTime.now().plusHours(1); // Not past due
        when(task.getDueDateTime()).thenReturn(dueDateTime);

        boolean result = strategy.shouldUpdate(task);

        assertFalse(result);
    }
}
