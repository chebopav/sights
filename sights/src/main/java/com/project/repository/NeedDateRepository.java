package com.project.repository;

import com.project.entity.data.NeedDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface NeedDateRepository extends PagingAndSortingRepository<NeedDate, LocalDate> {
}
