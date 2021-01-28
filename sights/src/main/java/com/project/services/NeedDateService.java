package com.project.services;

import com.project.entity.data.NeedDate;
import com.project.repository.NeedDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class NeedDateService {

    private NeedDateRepository repository;

    @Autowired
    public NeedDateService(NeedDateRepository repository) {
        this.repository = repository;
    }

    public NeedDate getNeedDateByDate(LocalDate date){
        Optional<NeedDate> needDate = repository.findById(date);
        return needDate.orElse(null);
    }

    public void updateDate(NeedDate date){
        repository.save(date);
    }
}
