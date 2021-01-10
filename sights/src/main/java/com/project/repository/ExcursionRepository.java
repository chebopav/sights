package com.project.repository;

import com.project.entity.data.Excursion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcursionRepository extends PagingAndSortingRepository<Excursion, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM excursion WHERE type = typ")
    public Iterable<Excursion> getExcursionByType(@Param("typ") int type);
}
