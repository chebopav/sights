package com.project.runnables.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class SchedulingTest {
    private ApplicationContext context;
    private TaskExecutor executor;

    private static final long delay = 10000L;

    @Autowired
    public SchedulingTest(ApplicationContext context, @Qualifier("executor") TaskExecutor executor) {
        this.context = context;
        this.executor = executor;
    }

    @Scheduled(fixedRate = 1_000_000_000L)
    public void start(){
        Test test = context.getBean(Test.class);
        executor.execute(test);
    }
}
