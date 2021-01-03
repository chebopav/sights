package com.project.repository;

import com.project.entity.data.Museum;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuseumRepository extends PagingAndSortingRepository<Museum, Long> {
}
