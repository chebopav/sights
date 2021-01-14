package com.project.services;

import com.project.entity.data.Theater;
import com.project.exceptions.DataException;
import com.project.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TheaterService {

    private TheaterRepository repository;

    @Autowired
    public TheaterService(TheaterRepository repository) {
        this.repository = repository;
    }

    public TheaterRepository getRepository() {
        return repository;
    }

    public Theater addTheater(Theater theater) throws DataException {
        if(repository.existsById(theater.getId())
                || repository.getTheaterByName(theater.getName()) != null){
            throw new DataException("Театр уже существует");
        }
        return repository.save(theater);
    }

    public Theater updateTheater(Theater theater) throws DataException {
        if(!repository.existsById(theater.getId())){
            throw new DataException("Театр не существует");
        }
        return repository.save(theater);
    }

    public Page<Theater> getPageOfTheaters(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Theater> theatersPage = repository.findAll(pageable);
        if (theatersPage.isEmpty()){
            throw new DataException("Записи театров не найдены");
        }
        return theatersPage;
    }

    public Optional<Theater> getTheaterById(long id) throws DataException {
        Optional<Theater> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Театр не найден");
        }
        return result;
    }

    public void deleteTheaterById(long id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("Театр не найден");
        }
        repository.deleteById(id);
    }

    public Theater getTheaterByName(String name) throws DataException {
        Theater result = repository.getTheaterByName(name);
        if (result == null)
            throw new DataException("Театр не найден");
        return result;
    }
}
