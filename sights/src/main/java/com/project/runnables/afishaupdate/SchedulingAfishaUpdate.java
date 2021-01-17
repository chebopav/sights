package com.project.runnables.afishaupdate;

import com.project.helpers_and_statics.Statics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulingAfishaUpdate {
    private TaskExecutor executor;
    private ApplicationContext context;

    @Autowired
    public SchedulingAfishaUpdate(@Qualifier("executor") TaskExecutor executor, ApplicationContext context) {
        this.executor = executor;
        this.context = context;
    }

    @Scheduled(initialDelay = 1000L, fixedRate = Statics.DAY_IN_MILLIS)
    public void start(){
        AfishaUpdate update = context.getBean(AfishaUpdate.class);
        executor.execute(update);
        MoscowAfishaUpdate moscowAfishaUpdate = context.getBean(MoscowAfishaUpdate.class);
        executor.execute(moscowAfishaUpdate);
    }


}