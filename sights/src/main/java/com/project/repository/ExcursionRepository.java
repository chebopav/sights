package com.project.repository;

import com.project.entity.data.Excursion;
import com.project.entity.data.address.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExcursionRepository extends PagingAndSortingRepository<Excursion, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM excursion WHERE type = typ")
    public Iterable<Excursion> getExcursionByType(@Param("typ") int type);

    @Query(value = "SELECT e FROM Excursion e WHERE e.name = :name")
    public Excursion findByName(@Param("name") String name);

    @Query(value = "SELECT e FROM Excursion e WHERE e.city = :city")
    public List<Excursion> getAllExcursionsOfCity(@Param("city") City city);

    @Query(nativeQuery = true,
            value = "SELECT * FROM excursion_dates INNER JOIN event ON (excursions_id = id) WHERE dates_date = :date")
    public List<Excursion> getAllEventsToDate(@Param("date") LocalDate date);
}
