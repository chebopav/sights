package com.project.controllers;

import com.project.entity.data.Sight;
import com.project.entity.data.Theater;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.SightRepository;
import com.project.repository.TheaterRepository;
import com.project.services.CityService;
import com.project.services.SightService;
import com.project.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@RequestMapping("/theaters")
public class TheaterController {
    private CityService cityService;
    private CityRepository cityRepository;
    private TheaterService theaterService;
    private TheaterRepository theaterRepository;

    @Autowired
    public TheaterController(CityService cityService,
                             CityRepository cityRepository,
                             TheaterService theaterService,
                             TheaterRepository theaterRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
        this.theaterService = theaterService;
        this.theaterRepository = theaterRepository;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("theater", new Theater());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("theaters", theaterRepository.findAll());
        return "add_theater";
    }

    @PostMapping(value = "/add")
    public String addTheater(@ModelAttribute("theater") @Valid Theater theater,
                           BindingResult bindingResult,
                           @RequestParam(name = "city_id") int city_id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cities", cityRepository.findAll());
            return "add_theater";
        }

        City city = cityRepository.findById(city_id).get();
        theater.setCity(city);

        try {
            theaterService.addTheater(theater);
        } catch (DataException e) {
            e.printStackTrace();
        }
        return "redirect:/theaters/add";
    }

    @GetMapping(value = "/del")
    public String delTheater(@RequestParam("id") int id) {
        try {
            theaterService.deleteTheaterById(id);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/theaters/add";
    }

    /*
    @GetMapping(value = "/list")
    public String listMuseum(Model model, @RequestParam("cityId") int cityId, @RequestParam("placeId") String placeId) {
        if (placeId.trim().equalsIgnoreCase("museum")) {
            model.addAttribute("museums", museumService.getAllMuseumsOfCity(cityId));
            return "museums_list";
        }
        else if (placeId.trim().equalsIgnoreCase("sight")) {
            model.addAttribute("sights", sightService.getAllSightsOfCity(cityId));
            return "sights_list";
        }
        else if (placeId.trim().equalsIgnoreCase("excursion")) {
            model.addAttribute("excursions", excursionService.getAllExcursionsOfCity(cityId));
            return "excursions_list";
        }
        else {
            model.addAttribute("theaters", theaterService.getAllTheatersOfCity(cityId));
            return "theaters_list";
        }
    }*/

    @GetMapping(value = "/view")
    public String viewTheater(Model model, @RequestParam("id") long id) {
        Theater theater;
        try {
            theater = theaterService.getTheaterById(id).orElse(null);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        model.addAttribute("theater", theater);
        /*model.addAttribute("comments", commentService.getAllCommentsById("museum", id));*/
        return "theater_view";
    }
}