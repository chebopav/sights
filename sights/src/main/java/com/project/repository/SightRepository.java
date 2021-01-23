package com.project.repository;

import com.project.entity.data.Sight;
import com.project.entity.data.address.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SightRepository extends PagingAndSortingRepository<Sight, Long> {

    @Query(value = "SELECT s FROM Sight s WHERE s.name = :name")
    public Sight getSightByName(@Param("name") String name);

    @Query(value = "SELECT s FROM Sight s WHERE city_id = :id")
    public Iterable<Sight> getSightsByCity(@Param("id") long id);

    @Query(nativeQuery = true, value = "SELECT * FROM sight ORDER BY RANDOM() LIMIT 1")
    public Sight getRandomSightFromAll();
}
