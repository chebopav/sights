package com.project.controllers;

import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public City[] selCitiesByCountry( BindingResult bindingResult,
                                     @RequestParam("id") int id) {
        List<City> cityList = new ArrayList<>();
        try {
            cityList = cityService.selectCitiesByCountry(id);
        } catch (DataException e) {
            e.printStackTrace();
        }
        City[] citiesArray = new City[cityList.size()];
        for (int i = 0; i < citiesArray.length; i ++){
            citiesArray[i] = cityList.get(i);
        }
        System.out.println(citiesArray);
        return citiesArray;
    }
}
