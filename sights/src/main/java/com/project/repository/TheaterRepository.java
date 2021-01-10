package com.project.repository;

import com.project.entity.data.Theater;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends PagingAndSortingRepository<Theater, Long> {

    @Query(value = "SELECT t FROM Theater t WHERE t.name = :name")
    public Theater getTheaterByName(@Param("name") String name);
}
