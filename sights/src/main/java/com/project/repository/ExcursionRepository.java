package com.project.repository;

import com.project.entity.data.Excursion;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcursionRepository extends PagingAndSortingRepository<Excursion, Long> {
}
