package com.project.repository;

import com.project.entity.data.address.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {

    @Query(value = "SELECT c FROM Country c WHERE UPPER(c.name) = :name")
    public Country getCountryByName(@Param("name") String name);
}
