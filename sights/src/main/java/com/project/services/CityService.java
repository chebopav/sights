package com.project.services;

import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    @Autowired
    public CityService(CityRepository repository, CountryRepository countryRepository) {
        this.cityRepository = repository;
        this.countryRepository = countryRepository;
    }

    public CityRepository getRepository() {
        return cityRepository;
    }

    public City addCity(City city) throws DataException{
        if(cityRepository.existsById(city.getId())
            || cityRepository.getCityByName(city.getName()) != null){
            throw new DataException("Город уже существует");
        }
        return cityRepository.save(city);
    }

    public City updateCity(City city) throws DataException {
        if(!cityRepository.existsById(city.getId())){
            throw new DataException("Город не существует");
        }
        return cityRepository.save(city);
    }

    public Page<City> getPageOfCities(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<City> cityPage = cityRepository.findAll(pageable);
        if (cityPage.isEmpty()){
            throw new DataException("Записи городов не найдены");
        }
        return cityPage;
    }

    public Optional<City> getCityById(int id) throws DataException {
        Optional<City> result = cityRepository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Город не найден");
        }
        return result;
    }

    public void deleteCityById(int id) throws DataException {
        if (!cityRepository.existsById(id)){
            throw new DataException("Город не найден");
        }
        cityRepository.deleteById(id);
    }

    public City getCityByName(String name) throws DataException {
        City result = cityRepository.getCityByName(name);
        if (result == null)
            throw new DataException("Город не найден");
        return result;
    }

    public Iterable<City> getAllCitiesOfCountry(Country country) throws DataException {
        Iterable<City> result = cityRepository.getAllCitiesOfCountry(country);
        if (result == null)
            throw new DataException("Города не найдены");
        return result;
    }

    public List<City> selectCitiesByCountry(int id) throws DataException {
        Country selectedCountry = countryRepository.findById(id).get();
        List<City> cityList = cityRepository.getAllCitiesOfCountry(selectedCountry);
        return cityList;
    }
}
