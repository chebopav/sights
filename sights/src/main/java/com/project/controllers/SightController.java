package com.project.controllers;

import com.project.entity.data.Museum;
import com.project.entity.data.Sight;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.SightRepository;
import com.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@RequestMapping("/sights")
public class SightController {
    private CityService cityService;
    private CityRepository cityRepository;
    private SightService sightService;
    private SightRepository sightRepository;

    @Autowired
    public SightController(CityService cityService,
                           CityRepository cityRepository,
                           SightService sightService) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
        this.sightService = sightService;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("sight", new Sight());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("sights", sightRepository.findAll());
        return "add_sight";
    }

    @PostMapping(value = "/add")
    public String addSight(@ModelAttribute("sight") @Valid Sight sight,
                            BindingResult bindingResult,
                            @RequestParam(name = "city_id") int city_id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cities", cityRepository.findAll());
            return "add_sight";
        }

        City city = cityRepository.findById(city_id).get();
        sight.setCity(city);

        try {
            sightService.addSight(sight);
        } catch (DataException e) {
            e.printStackTrace();
        }
        return "redirect:/sights/add";
    }

    @GetMapping(value = "/del")
    public String delSight(@RequestParam("id") int id) {
        try {
            sightService.deleteSightById(id);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/sights/add";
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
    public String viewSight(Model model, @RequestParam("id") long id) {
        Sight sight;
        try {
            sight = sightService.getSightById(id).orElse(null);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        model.addAttribute("sight", sight);
        /*model.addAttribute("comments", commentService.getAllCommentsById("museum", id));*/
        return "sight_view";
    }
}