package com.project.repository;

import com.project.entity.afisha.Event;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

}
