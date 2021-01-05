package com.project.repository;

import com.project.entity.data.Sight;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SightRepository extends PagingAndSortingRepository<Sight, Long> {

    @Query(value = "SELECT s FROM Sight s WHERE UPPER(s.name) = :name")
    public Sight getSightByName(@Param("name") String name);
}
