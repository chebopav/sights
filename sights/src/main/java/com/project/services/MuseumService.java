package com.project.services;

import com.project.entity.data.Museum;
import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.MuseumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MuseumService {

    private MuseumRepository repository;
    private CityRepository cityRepository;

    @Autowired
    public MuseumService(MuseumRepository repository, CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        this.repository = repository;
    }

    public MuseumRepository getRepository() {
        return repository;
    }

    public Museum addMuseum(Museum museum) throws DataException {
        if(repository.existsById(museum.getId())){
            throw new DataException("Музей уже существует");
        }
        return repository.save(museum);
    }

    public Museum updateMuseum(Museum museum) throws DataException {
        if(!repository.existsById(museum.getId())){
            throw new DataException("Музей не существует");
        }
        return repository.save(museum);
    }

    public Page<Museum> getPageOfMuseums(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Museum> museumPage = repository.findAll(pageable);
        if (museumPage.isEmpty()){
            throw new DataException("Записи музеев не найдены");
        }
        return museumPage;
    }

    public Optional<Museum> getMuseumById(long id) throws DataException {
        Optional<Museum> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Музей не найден");
        }
        return result;
    }

    public void deleteMuseumById(long id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("Музей не найден");
        }
        repository.deleteById(id);
    }

    public Museum getMuseumByName(String name) throws DataException {
        Museum result = repository.getMuseumByName(name);
        if (result == null)
            throw new DataException("Музей не найден");
        return result;
    }

    public List<Museum> getAllMuseumsOfCity(int cityId){
        City selectedCity = cityRepository.findById(cityId).get();
        return repository.getMuseumsByCity(selectedCity);
    }

    public Museum getRandomMuseumOnCity(City city){
        return repository.getRandomMuseumForCity(city.getId());
    }
}
