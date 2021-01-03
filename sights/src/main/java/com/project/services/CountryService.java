package com.project.services;

import com.project.entity.data.address.Country;
import com.project.exception.DataException;
import com.project.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {

    private CountryRepository repository;

    @Autowired
    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public CountryRepository getRepository() {
        return repository;
    }

    public Country addCountry(Country country){
        if(repository.existsById(country.getId())){
            throw new DataException("Страна уже существует");
        }
        return repository.save(country);
    }

    public Country updateCountry(Country country){
        if(!repository.existsById(country.getId())){
            throw new DataException("Страна не существует");
        }
        return repository.save(country);
    }

    public Page<Country> getPageOfCountries(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Country> countryPage = repository.findAll(pageable);
        if (countryPage.isEmpty()){
            throw new DataException("Записи стран не найдены");
        }
        return countryPage;
    }

    public Optional<Country> getCountryById(long id){
        Optional<Country> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Страна не найдена");
        }
        return result;
    }

    public void deleteCountryById(long id){
        if (!repository.existsById(id)){
            throw new DataException("Страна не найдена");
        }
        repository.deleteById(id);
    }
}
