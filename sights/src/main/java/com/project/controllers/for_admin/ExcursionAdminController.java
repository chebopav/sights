package com.project.controllers.for_admin;

import com.project.entity.data.Excursion;
import com.project.entity.data.Sight;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.CountryRepository;
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
@RequestMapping("/admin/excursions")
public class ExcursionAdminController {
    private CityRepository cityRepository;
    private ExcursionService excursionService;
    private ExcursionRepository excursionRepository;
    private CountryRepository countryRepository;

    @Autowired
    public ExcursionAdminController(CityRepository cityRepository,
                                    ExcursionService excursionService,
                                    ExcursionRepository excursionRepository,
                                    CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.excursionService = excursionService;
        this.excursionRepository = excursionRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("excursion", new Excursion());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("excursions", excursionRepository.findAll());
        return "add_excursion";
    }

    @PostMapping(value = "/add")
    public String addExcursion(@ModelAttribute("excursion") @Valid Excursion excursion,
                           BindingResult bindingResult,
                           @RequestParam(name = "cityId") int city_id, Model model) {
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
        return "redirect:/admin/excursions/add";
    }

    @GetMapping(value = "/del")
    public String delExcursion(@RequestParam("id") int id) {
        try {
            excursionService.deleteExcursionById(id);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/admin/excursions/add";
    }
}