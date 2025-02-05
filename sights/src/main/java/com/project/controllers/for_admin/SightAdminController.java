package com.project.controllers.for_admin;

import com.project.entity.data.Museum;
import com.project.entity.data.Sight;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.CountryRepository;
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
@RequestMapping("/admin/sights")
public class SightAdminController {
    private CityRepository cityRepository;
    private SightService sightService;
    private SightRepository sightRepository;
    private CountryRepository countryRepository;

    @Autowired
    public SightAdminController(CityRepository cityRepository,
                                SightService sightService,
                                SightRepository sightRepository,
                                CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.sightService = sightService;
        this.sightRepository = sightRepository;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("sight", new Sight());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("sights", sightRepository.findAll());
        return "add_sight";
    }

    @PostMapping(value = "/add")
    public String addSight(@ModelAttribute("sight") @Valid Sight sight,
                            BindingResult bindingResult,
                            @RequestParam(name = "cityId") int city_id, Model model) {
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
        return "redirect:/admin/sights/add";
    }

    @GetMapping(value = "/del")
    public String delSight(@RequestParam("id") int id) {
        try {
            sightService.deleteSightById(id);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/admin/sights/add";
    }
}