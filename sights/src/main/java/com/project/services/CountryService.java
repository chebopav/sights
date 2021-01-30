package com.project.services;

import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import com.project.exceptions.DataException;
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

    public boolean saveCountry(Country country){
        Country loaded = repository.getCountryByName(country.getName());
        if (loaded != null){
            return false;
        }
        repository.save(country);
        return true;
    }

    public Country addCountry(Country country) throws DataException {
        if(repository.existsById(country.getId())
            || repository.getCountryByName(country.getName()) != null){
            throw new DataException("Страна уже существует");
        }
        return repository.save(country);
    }

    public Country updateCountry(Country country) throws DataException {
        if(!repository.existsById(country.getId())){
            throw new DataException("Страна не существует");
        }
        return repository.save(country);
    }

    public Page<Country> getPageOfCountries(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Country> countryPage = repository.findAll(pageable);
        if (countryPage.isEmpty()){
            throw new DataException("Записи стран не найдены");
        }
        return countryPage;
    }

    public Optional<Country> getCountryById(int id) throws DataException {
        Optional<Country> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Страна не найдена");
        }
        return result;
    }

    public void deleteCountryById(int id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("Страна не найдена");
        }
        repository.deleteById(id);
    }

    public Country getCountryByName(String name) throws DataException {
        Country result = repository.getCountryByName(name);
        if (result == null)
            throw new DataException("Страна не найдена");
        return result;
    }
}
