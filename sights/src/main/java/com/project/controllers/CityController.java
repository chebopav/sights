package com.project.controllers;

import com.project.entity.data.BaseData;
import com.project.entity.data.Museum;
import com.project.entity.data.Sight;
import com.project.entity.data.Theater;
import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import com.project.exceptions.DataException;
import com.project.repository.*;
import com.project.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/cities")
public class CityController {
    private CityService service;
    private CityRepository repository;
    private CountryRepository countryRepository;
    private ApplicationContext context;

    @Autowired
    public CityController(CityService service,
                          CityRepository repository,
                          CountryRepository countryRepository,
                          ApplicationContext context) {
        this.service = service;
        this.repository = repository;
        this.countryRepository = countryRepository;
        this.context = context;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("city", new City());
        model.addAttribute("countries", countryRepository.findAll());
        return "add_city";
    }

    @PostMapping(value = "/add")
    public String addCity(@ModelAttribute("city") @Valid City city,
                          BindingResult bindingResult,
                          @RequestParam(name = "countryId") int countryId, Model model) {
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("countries", countryRepository.findAll());
            return "add_city";
        }

        Country country = countryRepository.findById(countryId).get();

        country.getCities().add(city);
        city.setCountry(country);

        countryRepository.save(country);
        repository.save(city);

        return "redirect:/cities/add";
    }

    @GetMapping(value = "/all")
    public String getAllCities(Model model, @RequestParam("page") int page, @RequestParam("size") int size){
        try {
            Page<City> cityPage = service.getPageOfCities(page, size);
            model.addAttribute("cities", cityPage);
        } catch (DataException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "all_cities";
    }

    @GetMapping(value = "/all/events")
    public String getAllSightsOfCity(Model model, @RequestParam("page") int page, @RequestParam("size") int size){
        MuseumRepository museumRepository = context.getBean(MuseumRepository.class);
        SightRepository sightRepository = context.getBean(SightRepository.class);
        TheaterRepository theaterRepository = context.getBean(TheaterRepository.class);
        Iterable<Museum> museums = museumRepository.findAll();
        Iterable<Sight> sights = sightRepository.findAll();
        Iterable<Theater> theaters = theaterRepository.findAll();
        List<BaseData> list = new ArrayList<>();
        for (Museum museum : museums) {
            list.add(museum);
        }
        for (Sight sight : sights) {
            list.add(sight);
        }
        for (Theater theater : theaters) {
            list.add(theater);
        }
        Collections.shuffle(list);
        Pageable pageable = PageRequest.of(page, size);
        Page<BaseData> p = new PageImpl<>(list, pageable, list.size());
        System.out.println(p.getTotalPages());
        model.addAttribute("sights", p);
        return "all_events";
    }
}
