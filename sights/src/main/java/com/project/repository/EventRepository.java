package com.project.repository;

import com.project.entity.afisha.Event;
import com.project.entity.data.NeedDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    //@Query(value = "SELECT e FROM Event e WHERE e.dates = :date")
    @Query(nativeQuery = true, value = "SELECT * FROM event INNER JOIN event_dates WHERE dates_date = :date")
    public List<Event> getAllEventsToDate(@Param("date")LocalDate date);
}
