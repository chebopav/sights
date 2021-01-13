package com.project.controllers;

import com.project.entity.data.BaseData;
import com.project.entity.data.Museum;
import com.project.entity.data.Sight;
import com.project.entity.data.Theater;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.MuseumRepository;
import com.project.repository.SightRepository;
import com.project.repository.TheaterRepository;
import com.project.services.CityService;
import com.project.services.MuseumService;
import com.project.services.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/city")
public class CityController {
    private CityService service;
    private CityRepository repository;
    private ApplicationContext context;

    @Autowired
    public CityController(CityService service, CityRepository repository, ApplicationContext context) {
        this.service = service;
        this.repository = repository;
        this.context = context;
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

        MuseumService museumService = context.getBean(MuseumService.class);
        MuseumRepository museumRepository = context.getBean(MuseumRepository.class);
        SightService sightService = context.getBean(SightService.class);
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
        System.out.println(list.size());
        Pageable pageable = PageRequest.of(page, size);
        Page<BaseData> result = Page.empty(pageable);
        result.getContent();
        model.addAttribute("sights", result);
        return "all_events";
    }


}
