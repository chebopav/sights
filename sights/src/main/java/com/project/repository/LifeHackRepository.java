package com.project.repository;

import com.project.entity.data.LifeHack;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeHackRepository extends PagingAndSortingRepository<LifeHack, Long> {
}
