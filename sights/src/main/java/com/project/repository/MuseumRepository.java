package com.project.repository;

import com.project.entity.data.Museum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MuseumRepository extends PagingAndSortingRepository<Museum, Long> {

    @Query(value = "SELECT m FROM Museum m WHERE m.name = :name")
    public Museum getMuseumByName(@Param("name") String name);
}