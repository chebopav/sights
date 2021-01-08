package com.project.runnables.afishaupdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.annotation.Transient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Scheduling {
    private TaskExecutor executor;
    private ApplicationContext context;

    @Transient
    private static final long dayTime = 1_000 * 60 * 60 * 24;

    @Autowired
    public Scheduling(@Qualifier("executor") TaskExecutor executor, ApplicationContext context) {
        this.executor = executor;
        this.context = context;
    }

    @Scheduled(fixedRate = dayTime)
    public void start(){
        AfishaUpdate update = context.getBean(AfishaUpdate.class);
        executor.execute(update);
    }


}
