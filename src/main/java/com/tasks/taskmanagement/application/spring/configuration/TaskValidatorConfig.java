package com.tasks.taskmanagement.application.spring.configuration;

import com.tasks.taskmanagement.application.spring.validator.*;
import com.tasks.taskmanagement.domain.validator.TaskValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The TaskValidatorConfig class is a Spring configuration class responsible for defining and configuring
 * task validators used in the application.
 */
@Configuration
public class TaskValidatorConfig {

    /**
     * Create and configure a composite task validator for delete operations.
     *
     * @param taskIsNotDoneChecker    Validator to check if the task is not marked as done.
     * @param taskIsNotPastDueChecker Validator to check if the task is not past its due date.
     * @return A composite task validator for delete operations.
     */
    @Bean
    @Qualifier("compositForDeleteTaskValidator")
    public TaskValidator compositForDeleteTaskValidator(
            TaskIsNotDoneChecker taskIsNotDoneChecker,
            TaskIsNotPastDueChecker taskIsNotPastDueChecker) {
        CompositTaskValidator composite = new CompositTaskValidator();
        composite.addValidator(taskIsNotDoneChecker);
        composite.addValidator(taskIsNotPastDueChecker);
        return composite;
    }

    /**
     * Create and configure a composite task validator for "Not Done Not Pass Due" operations.
     *
     * @param taskIsNotDoneChecker    Validator to check if the task is not marked as done.
     * @param taskIsNotPastDueChecker Validator to check if the task is not past its due date.
     * @return A composite task validator for "Not Done Not Pass Due" operations.
     */
    @Bean
    @Qualifier("compositNotDoneNotPassDue")
    public TaskValidator compositNotDoneNotPassDue(
            TaskIsNotDoneChecker taskIsNotDoneChecker,
            TaskIsNotPastDueChecker taskIsNotPastDueChecker) {
        CompositTaskValidator composite = new CompositTaskValidator();
        composite.addValidator(taskIsNotDoneChecker);
        composite.addValidator(taskIsNotPastDueChecker);
        return composite;
    }

    /**
     * Create and configure a composite task validator for save operations.
     *
     * @param taskIsNotNotDoneOrDeadlineNotExpired Validator to check if the task is not marked as done or the deadline has not expired.
     * @param taskIsNotPastDueOrDeadlineExpired     Validator to check if the task is not past its due date or the deadline has not expired.
     * @param taskIsDoneInTheFuture                 Validator to check if the task is not marked as done in the future.
     * @return A composite task validator for save operations.
     */
    @Bean
    @Qualifier("compositForSave")
    public TaskValidator compositForSave(
            TaskIsNotNotDoneOrDeadlineNotExpired taskIsNotNotDoneOrDeadlineNotExpired,
            TaskIsNotPastDueOrDeadlineExpired taskIsNotPastDueOrDeadlineExpired,
            TaskIsDoneInTheFuture taskIsDoneInTheFuture) {
        CompositTaskValidator composite = new CompositTaskValidator();
        composite.addValidator(taskIsNotNotDoneOrDeadlineNotExpired);
        composite.addValidator(taskIsNotPastDueOrDeadlineExpired);
        composite.addValidator(taskIsDoneInTheFuture);
        return composite;
    }
}
