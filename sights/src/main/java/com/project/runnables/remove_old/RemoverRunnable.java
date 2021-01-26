package com.project.runnables.remove_old;

import com.project.entity.afisha.Event;
import com.project.entity.data.Excursion;
import com.project.repository.EventRepository;
import com.project.repository.ExcursionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class RemoverRunnable implements Runnable{
    @Autowired
    private ApplicationContext context;

    @Override
    public void run() {
        List<Event> events = context.getBean(EventRepository.class).getAllEventsToDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
        List<Excursion> excursions = context.getBean(ExcursionRepository.class).getAllEventsToDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
        for (Excursion excursion : excursions) {
            context.getBean(ExcursionRepository.class).delete(excursion);
        }

        for (Event event : events) {
            context.getBean(EventRepository.class).delete(event);
        }
    }
}
