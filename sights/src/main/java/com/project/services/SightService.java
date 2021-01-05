package com.project.services;

import com.project.entity.data.Sight;
import com.project.exceptions.DataException;
import com.project.repository.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SightService {

    private SightRepository repository;

    @Autowired
    public SightService(SightRepository repository) {
        this.repository = repository;
    }

    public SightRepository getRepository() {
        return repository;
    }

    public Sight addSight(Sight sight) throws DataException {
        if(repository.existsById(sight.getId())){
            throw new DataException("Достопримечательность уже существует");
        }
        return repository.save(sight);
    }

    public Sight updateSight(Sight sight) throws DataException {
        if(!repository.existsById(sight.getId())){
            throw new DataException("Достопримечательность не существует");
        }
        return repository.save(sight);
    }

    public Page<Sight> getPageOfSights(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Sight> sightPage = repository.findAll(pageable);
        if (sightPage.isEmpty()){
            throw new DataException("Записи достопримечательностей не найдены");
        }
        return sightPage;
    }

    public Optional<Sight> getSightById(long id) throws DataException {
        Optional<Sight> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Достопримечательность не найдена");
        }
        return result;
    }

    public void deleteSightById(long id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("Достопримечательность не найдена");
        }
        repository.deleteById(id);
    }
}
