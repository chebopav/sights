package com.project.services;

import com.project.entity.afisha.Event;
import com.project.entity.data.NeedDate;
import com.project.exceptions.DataException;
import com.project.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService {
    private EventRepository repository;

    @Autowired
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public EventRepository getRepository() {
        return repository;
    }

    public Event addEvent(Event event) throws DataException {
        if(repository.existsById(event.getId())){
            throw new DataException("Мероприятие уже существует");
        }
        return repository.save(event);
    }

    public Event updateEvent(Event event) throws DataException {
        if(!repository.existsById(event.getId())){
            throw new DataException("Мероприятие не существует");
        }
        return repository.save(event);
    }

    public Page<Event> getPageOfEvents(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = repository.findAll(pageable);
        if (eventsPage.isEmpty()){
            throw new DataException("Записи меропритий не найдены");
        }
        return eventsPage;
    }

    public Optional<Event> getEventById(long id) throws DataException {
        Optional<Event> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Мероприятие не найдено");
        }
        return result;
    }

    public void deleteEventById(long id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("Мероприятие не найдено");
        }
        repository.deleteById(id);
    }

    public List<Event> getAllEventsToDate(NeedDate date){
        return repository.getAllEventsToDate(date.getDate());
    }

}
