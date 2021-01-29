package com.project.controllers.for_admin;

import com.project.entity.data.Theater;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.CountryRepository;
import com.project.repository.TheaterRepository;
import com.project.services.CityService;
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
@RequestMapping("/admin/theaters")
public class TheaterAdminController {
    private CityRepository cityRepository;
    private TheaterService theaterService;
    private TheaterRepository theaterRepository;
    private CountryRepository countryRepository;

    @Autowired
    public TheaterAdminController(CityRepository cityRepository,
                                  TheaterService theaterService,
                                  TheaterRepository theaterRepository,
                                  CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.theaterService = theaterService;
        this.theaterRepository = theaterRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("theater", new Theater());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("theaters", theaterRepository.findAll());
        return "add_theater";
    }

    @PostMapping(value = "/add")
    public String addTheater(@ModelAttribute("theater") @Valid Theater theater,
                           BindingResult bindingResult,
                           @RequestParam(name = "cityId") int city_id, Model model) {
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
        return "redirect:/admin/theaters/add";
    }

    @GetMapping(value = "/del")
    public String delTheater(@RequestParam("id") int id) {
        try {
            theaterService.deleteTheaterById(id);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/admin/theaters/add";
    }
}