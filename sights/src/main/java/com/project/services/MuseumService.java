package com.project.services;

import com.project.entity.data.Museum;
import com.project.exception.DataException;
import com.project.repository.MuseumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MuseumService {

    private MuseumRepository repository;

    @Autowired
    public MuseumService(MuseumRepository repository) {
        this.repository = repository;
    }

    public MuseumRepository getRepository() {
        return repository;
    }

    public Museum addMuseum(Museum museum){
        if(repository.existsById(museum.getId())){
            throw new DataException("Музей уже существует");
        }
        return repository.save(museum);
    }

    public Museum updateMuseum(Museum museum){
        if(!repository.existsById(museum.getId())){
            throw new DataException("Музей не существует");
        }
        return repository.save(museum);
    }

    public Page<Museum> getPageOfMuseums(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Museum> museumPage = repository.findAll(pageable);
        if (museumPage.isEmpty()){
            throw new DataException("Записи музеев не найдены");
        }
        return museumPage;
    }

    public Optional<Museum> getMuseumById(long id){
        Optional<Museum> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Музей не найден");
        }
        return result;
    }

    public void deleteMuseumById(long id){
        if (!repository.existsById(id)){
            throw new DataException("Музей не найден");
        }
        repository.deleteById(id);
    }
}
