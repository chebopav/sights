package com.project.controllers;

import com.project.entity.data.Excursion;
import com.project.entity.data.Sight;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.ExcursionRepository;
import com.project.repository.SightRepository;
import com.project.services.CityService;
import com.project.services.ExcursionService;
import com.project.services.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@RequestMapping("/excursions")
public class ExcursionController {
    private CityService cityService;
    private CityRepository cityRepository;
    private ExcursionService excursionService;
    private ExcursionRepository excursionRepository;

    @Autowired
    public ExcursionController(CityService cityService,
                           CityRepository cityRepository,
                           ExcursionService excursionService,
                           ExcursionRepository excursionRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
        this.excursionService = excursionService;
        this.excursionRepository = excursionRepository;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("excursion", new Excursion());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("excursions", excursionRepository.findAll());
        return "add_excursion";
    }

    @PostMapping(value = "/add")
    public String addExcursion(@ModelAttribute("excursion") @Valid Excursion excursion,
                           BindingResult bindingResult,
                           @RequestParam(name = "city_id") int city_id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cities", cityRepository.findAll());
            return "add_excursion";
        }

        City city = cityRepository.findById(city_id).get();
        excursion.setCity(city);

        try {
            excursionService.addExcursion(excursion);
        } catch (DataException e) {
            e.printStackTrace();
        }
        return "redirect:/excursions/add";
    }

    @GetMapping(value = "/del")
    public String delExcursion(@RequestParam("id") int id) {
        try {
            excursionService.deleteExcursionById(id);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/excursions/add";
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
    public String viewExcursion(Model model, @RequestParam("id") long id) {
        Excursion excursion;
        try {
            excursion = excursionService.getExcursionById(id).orElse(null);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        model.addAttribute("excursion", excursion);
        /*model.addAttribute("comments", commentService.getAllCommentsById("museum", id));*/
        return "excursion_view";
    }
}