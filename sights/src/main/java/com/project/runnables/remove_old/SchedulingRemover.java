package com.project.runnables.remove_old;

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
public class SchedulingRemover {
    private TaskExecutor executor;
    private ApplicationContext context;

    @Autowired
    public SchedulingRemover(@Qualifier("executor") TaskExecutor executor, ApplicationContext context) {
        this.executor = executor;
        this.context = context;
    }

    @Scheduled(fixedRate = Statics.DAY_IN_MILLIS)
    public void start(){
        RemoverRunnable runnable = context.getBean(RemoverRunnable.class);
        executor.execute(runnable);
    }
}
