package com.project.repository;

import com.project.entity.data.Museum;
import com.project.entity.data.address.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuseumRepository extends PagingAndSortingRepository<Museum, Long> {

    @Query(value = "SELECT m FROM Museum m WHERE m.name = :name")
    public Museum getMuseumByName(@Param("name") String name);

    @Query(value = "SELECT m FROM Museum m WHERE m.city = :city")
    public List<Museum> getMuseumsByCity(@Param("city") City city);

    @Query(nativeQuery = true, value = "SELECT * FROM museum WHERE city_id = :id ORDER BY RANDOM() LIMIT 1")
    public Museum getRandomMuseumForCity(@Param("id") int cityId);
}
