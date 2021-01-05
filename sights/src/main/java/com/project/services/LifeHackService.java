package com.project.services;

import com.project.entity.data.LifeHack;
import com.project.exceptions.DataException;
import com.project.repository.LifeHackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LifeHackService {

    LifeHackRepository repository;

    public LifeHackService(LifeHackRepository repository) {
        this.repository = repository;
    }

    public LifeHackRepository getRepository() {
        return repository;
    }

    public LifeHack addLifeHack(LifeHack lifeHack) throws DataException {
        if(repository.existsById(lifeHack.getId())){
            throw new DataException("Лайфхак уже существует");
        }
        return repository.save(lifeHack);
    }

    public LifeHack updateLifeHack(LifeHack lifeHack) throws DataException {
        if(!repository.existsById(lifeHack.getId())){
            throw new DataException("Лайфхак не существует");
        }
        return repository.save(lifeHack);
    }

    public Page<LifeHack> getPageOfLifeHacks(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<LifeHack> lifeHackPage = repository.findAll(pageable);
        if (lifeHackPage.isEmpty()){
            throw new DataException("Записи лайфхаков не найдены");
        }
        return lifeHackPage;
    }

    public Optional<LifeHack> getLifeHackById(long id) throws DataException {
        Optional<LifeHack> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Лайфхак не найден");
        }
        return result;
    }

    public void deleteLifeHackById(long id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("Лайфхак не найден");
        }
        repository.deleteById(id);
    }
}
