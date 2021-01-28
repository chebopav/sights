package com.project.controllers.for_user;

import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/citiesOfCountry")
public class CitiesByCountryController {
    private CityService cityService;

    @Autowired
    public CitiesByCountryController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = "/sel")
    public List<City> selCitiesByCountry(@RequestParam("id") int id) {
        List<City> cityList = new ArrayList<>();
        try {
            cityList = cityService.selectCitiesByCountry(id);
        } catch (DataException e) {
            e.printStackTrace();
        }
        return cityList;
    }
}
