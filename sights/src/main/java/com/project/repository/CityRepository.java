package com.project.repository;

import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Long> {

    @Query(value = "SELECT c FROM City c WHERE c.name = :name")
    public City getCityByName(@Param("name") String name);

    @Query(value = "SELECT c FROM City c WHERE c.country = :country")
    public Iterable<City> getAllCitiesOfCountry(@Param("country")Country country);
}
