package com.project.services;

import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    private CityRepository repository;

    @Autowired
    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public CityRepository getRepository() {
        return repository;
    }

    public City addCity(City city) throws DataException{
        if(repository.existsById(city.getId())){
            throw new DataException("Город уже существует");
        }
        return repository.save(city);
    }

    public City updateCity(City city) throws DataException {
        if(!repository.existsById(city.getId())){
            throw new DataException("Город не существует");
        }
        return repository.save(city);
    }

    public Page<City> getPageOfCities(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<City> cityPage = repository.findAll(pageable);
        if (cityPage.isEmpty()){
            throw new DataException("Записи городов не найдены");
        }
        return cityPage;
    }

    public Optional<City> getCityById(long id) throws DataException {
        Optional<City> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Город не найден");
        }
        return result;
    }

    public void deleteCityById(long id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("Город не найден");
        }
        repository.deleteById(id);
    }
}
