package com.project.controllers;

import com.project.entity.data.Museum;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.MuseumRepository;
import com.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/museums")
public class MuseumController {
    private CityService cityService;
    private CityRepository cityRepository;
    private MuseumService museumService;
    private MuseumRepository museumRepository;
    private ExcursionService excursionService;
    private SightService sightService;
    private TheaterService theaterService;

    @Autowired
    public MuseumController(CityService cityService,
                            CityRepository cityRepository,
                            MuseumRepository museumRepository,
                            MuseumService museumService,
                            ExcursionService excursionService,
                            TheaterService theaterService,
                            SightService sightService) {
        this.cityService = cityService;
        this.museumService = museumService;
        this.cityRepository = cityRepository;
        this.museumRepository = museumRepository;
        this.excursionService = excursionService;
        this.sightService = sightService;
        this.theaterService = theaterService;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("museum", new Museum());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("museums", museumRepository.findAll());
        return "add_museum";
    }

    @PostMapping(value = "/add")
    public String addMuseum(@ModelAttribute("museum") @Valid Museum museum,
                          BindingResult bindingResult,
                          @RequestParam(name = "city_id") int city_id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cities", cityRepository.findAll());
            return "add_museum";
        }

        City city = cityRepository.findById(city_id).get();
        museum.setCity(city);

        try {
            museumService.addMuseum(museum);
        } catch (DataException e) {
            e.printStackTrace();
        }
        return "redirect:/museums/add";
    }

    @GetMapping(value = "/del")
    public String delMuseum(@ModelAttribute("museum") @Valid Museum museum,
                          BindingResult bindingResult,
                          @RequestParam("id") int id) {
        try {
            museumService.deleteMuseumById(id);
        } catch (DataException e) {
            e.printStackTrace();
        }
        return "redirect:/museums/add";
    }

    @GetMapping(value = "/list")
    public String listMuseum(@ModelAttribute("museum") @Valid Museum museum, Model model,
                                   BindingResult bindingResult,
                                   @RequestParam("cityId") int cityId, @RequestParam("placeId") String placeId) {
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
    }

    @GetMapping(value = "/view")
    public String viewMuseum(@ModelAttribute("museum") @Valid Museum museum, Model model,
                             BindingResult bindingResult,
                             @RequestParam("id") long id) {
        try {
            model.addAttribute("museum", museumService.getMuseumById(id));
        } catch (DataException e) {
            e.printStackTrace();
        }
        return "museum_view";
    }
}
