package com.project.repository;

import com.project.entity.afisha.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM event_dates INNER JOIN event ON (events_id = id) WHERE dates_date = :date")
    public List<Event> getAllEventsToDate(@Param("date")LocalDate date);

    @Query(value = "SELECT e FROM Event e WHERE e.name = :name")
    public Event getEventByName(@Param("name") String name);

}
