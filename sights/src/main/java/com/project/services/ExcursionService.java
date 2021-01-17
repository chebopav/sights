package com.project.services;

import com.project.entity.data.Excursion;
import com.project.exceptions.DataException;
import com.project.repository.ExcursionRepository;
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

    public Excursion addExcursion(Excursion excursion) throws DataException {
        if(repository.existsById(excursion.getId())){
            throw new DataException("Экскурсия уже существует");
        }
        return repository.save(excursion);
    }

    public Excursion updateExcursion(Excursion excursion) throws DataException {
        if(!repository.existsById(excursion.getId())){
            throw new DataException("Экскурсия не существует");
        }
        return repository.save(excursion);
    }

    public Page<Excursion> getPageOfExcursions(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Excursion> excursionPage = repository.findAll(pageable);
        if (excursionPage.isEmpty()){
            throw new DataException("Записи экскурсий не найдены");
        }
        return excursionPage;
    }

    public Optional<Excursion> getExcursionById(long id) throws DataException {
        Optional<Excursion> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Экскурсия не найдена");
        }
        return result;
    }

    public void deleteExcursionById(long id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("Экскурсия не найдена");
        }
        repository.deleteById(id);
    }

    public Iterable<Excursion> getExcursionsByType(Excursion.Type type) throws DataException {
        int i = type.ordinal();
        Iterable<Excursion> result = repository.getExcursionByType(i);
        if (result == null)
            throw new DataException("Экскурсии не найдены");
        return result;
    }

    public Excursion getExcursionByName(String name) throws DataException {
        Excursion result = repository.findByName(name);
        if (result == null)
            throw new DataException("Экскурсия не найдена");
        return result;
    }
}
