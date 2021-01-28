package com.project.runnables.remove_old;

import com.project.entity.afisha.Event;
import com.project.entity.data.Excursion;
import com.project.entity.data.NeedDate;
import com.project.repository.EventRepository;
import com.project.repository.ExcursionRepository;
import com.project.repository.NeedDateRepository;
import com.project.services.NeedDateService;
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
        LocalDate forRemove = LocalDate.now().minus(1, ChronoUnit.DAYS);
        NeedDate removeDate = context.getBean(NeedDateService.class).getNeedDateByDate(forRemove);
        List<Event> events = context.getBean(EventRepository.class).getAllEventsToDate(forRemove);
        List<Excursion> excursions = context.getBean(ExcursionRepository.class).getAllEventsToDate(forRemove);
        for (Excursion excursion : excursions) {
            if (excursion.getDates().size() == 1) {
                context.getBean(ExcursionRepository.class).delete(excursion);
            } else {
                excursion.getDates().remove(removeDate);
            }
        }

        for (Event event : events) {
            if (event.getDates().size() == 1) {
                context.getBean(EventRepository.class).delete(event);
            } else {
                event.getDates().remove(removeDate);
            }
        }
        if (removeDate != null)
            context.getBean(NeedDateRepository.class).delete(removeDate);
    }
}
