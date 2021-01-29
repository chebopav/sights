package com.project.controllers.for_admin;

import com.project.entity.data.address.City;
import com.project.entity.data.address.Country;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.CountryRepository;
import com.project.services.CityService;
import com.project.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/cities")
public class CityAdminController {
    private CityService cityService;
    private CountryService countryService;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;
    private ApplicationContext context;

    @Autowired
    public CityAdminController(CityService cityService,
                               CountryService countryService,
                               CityRepository cityRepository,
                               CountryRepository countryRepository,
                               ApplicationContext context) {
        this.cityService = cityService;
        this.countryService = countryService;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.context = context;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("city", new City());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("countries", countryRepository.findAll());
        return "add_city";
    }

    @PostMapping(value = "/add")
    public String addCity(@ModelAttribute("city") @Valid City city,
                          BindingResult bindingResult,
                          @RequestParam(name = "countryId") int countryId, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("countries", countryRepository.findAll());
            return "add_city";
        }

        Country country = countryRepository.findById(countryId).orElse(null);
        if (country == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        country.getCities().add(city);
        city.setCountry(country);

        countryRepository.save(country);

        if (!cityService.saveCity(city)){
            model.addAttribute("nameError", "Такой город уже существует");
            model.addAttribute("cities", cityRepository.findAll());
            model.addAttribute("countries", countryRepository.findAll());
            return "add_city";
        }
        return "redirect:/admin/cities/add";
    }

    @GetMapping(value = "/del")
    public String delCity(@RequestParam("id") int id) {
        try {
            cityService.deleteCityById(id);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/admin/cities/add";
    }
}