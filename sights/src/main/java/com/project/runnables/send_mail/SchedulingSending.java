package com.project.runnables.send_mail;

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
public class SchedulingSending {
    private ApplicationContext context;
    private TaskExecutor executor;

    @Autowired
    public SchedulingSending(ApplicationContext context, @Qualifier("executor") TaskExecutor executor) {
        this.context = context;
        this.executor = executor;
    }

    @Scheduled(initialDelay = 10_000L, fixedRate = Statics.DAY_IN_MILLIS)
    public void start(){
        SendMail sendMail = context.getBean(SendMail.class);
        executor.execute(sendMail);
    }


}
