package com.project.services;

import com.project.entity.data.Excursion;
import com.project.entity.data.Sight;
import com.project.exception.DataException;
import com.project.repository.ExcursionRepository;
import com.project.repository.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExcursionService {

    private ExcursionRepository repository;

    @Autowired
    public ExcursionService(ExcursionRepository repository) {
        this.repository = repository;
    }


    public ExcursionRepository getRepository() {
        return repository;
    }

    public Excursion addExcursion(Excursion excursion){
        if(repository.existsById(excursion.getId())){
            throw new DataException("Экскурсия уже существует");
        }
        return repository.save(excursion);
    }

    public Excursion updateExcursion(Excursion excursion){
        if(!repository.existsById(excursion.getId())){
            throw new DataException("Экскурсия не существует");
        }
        return repository.save(excursion);
    }

    public Page<Excursion> getPageOfExcursions(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Excursion> excursionPage = repository.findAll(pageable);
        if (excursionPage.isEmpty()){
            throw new DataException("Записи экскурсий не найдены");
        }
        return excursionPage;
    }

    public Optional<Excursion> getExcursionById(long id){
        Optional<Excursion> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Экскурсия не найдена");
        }
        return result;
    }

    public void deleteExcursionById(long id){
        if (!repository.existsById(id)){
            throw new DataException("Экскурсия не найдена");
        }
        repository.deleteById(id);
    }
}
