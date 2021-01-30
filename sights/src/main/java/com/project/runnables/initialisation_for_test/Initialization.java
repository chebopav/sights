package com.project.runnables.initialisation_for_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class Initialization {
    private TaskExecutor executor;
    private ApplicationContext context;

    @Autowired
    public Initialization(@Qualifier("executor") TaskExecutor executor, ApplicationContext context) {
        this.executor = executor;
        this.context = context;
    }

    @Bean
    public void start(){
        InitRunnable runnable = context.getBean(InitRunnable.class);
        //executor.execute(runnable);
    }
}
