package com.tasks.taskmanagement.application.spring.service.statusupdate;

import com.tasks.taskmanagement.domain.entity.Task;
import com.tasks.taskmanagement.domain.repository.TaskRepository;
import com.tasks.taskmanagement.domain.strategy.StatusUpdateStrategy;
import com.tasks.taskmanagement.domain.valueobject.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TaskStatusUpdaterImpl implements TaskStatusUpdater{
    private final StatusUpdateStrategy statusUpdateStrategy;
    private final TaskRepository taskRepository;

    @Value("${statusupdater.batch.size}")
    private int batchSize;


    @Autowired
    public TaskStatusUpdaterImpl(StatusUpdateStrategy pastDueStatusUpdateStrategy, TaskRepository taskRepository) {
        this.statusUpdateStrategy = pastDueStatusUpdateStrategy;
        this.taskRepository = taskRepository;
    }

    public void updateStatus(Task task) {
        log.info("Task status updated to PAST_DUE for task with ID: {}", task.getId());
        if (statusUpdateStrategy.shouldUpdate(task)) {
            task.setStatus(TaskStatus.PAST_DUE);
            log.info("Task status updated to PAST_DUE for task with ID: {}", task.getId());
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateStatusAll(){
        List<Task> tasks = taskRepository
                .findByStatusAndDueDateTimeBefore(TaskStatus.NOT_DONE, LocalDateTime.now(), batchSize);;
        tasks.forEach(this::updateStatus);
        if(!tasks.isEmpty()){
            taskRepository.saveAll(tasks);
            log.info("Updated status for {} tasks to PAST_DUE.", tasks.size());
        }else {
            log.info("There are no tasks to update the status.");
        }
    }
}
