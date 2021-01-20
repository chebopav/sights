package com.project.controllers;

import com.project.repository.CityRepository;
import com.project.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private CountryRepository countryRepository;
    private CityRepository cityRepository;

    @Autowired
    public IndexController(CountryRepository countryRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        return "index";
    }

}
