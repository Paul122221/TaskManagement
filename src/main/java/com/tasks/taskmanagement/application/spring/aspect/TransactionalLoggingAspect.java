package com.tasks.taskmanagement.application.spring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TransactionalLoggingAspect {

    @AfterThrowing(pointcut = "@annotation(org.springframework.transaction.annotation.Transactional)", throwing = "ex")
    public void logAfterTransactionFailure(Exception ex) {
        log.error("Transaction failed with exception: {}", ex.getMessage());
    }
}
