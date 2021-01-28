package com.project.controllers.for_admin;

import com.project.entity.data.address.Country;
import com.project.exceptions.DataException;
import com.project.repository.CountryRepository;
import com.project.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/countries")
public class CountryAdminController {
    private CountryService service;
    private CountryRepository repository;

    @Autowired
    public CountryAdminController(CountryService service, CountryRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model) {
        model.addAttribute("country", new Country());
        model.addAttribute("countries", repository.findAll());
        return "add_country";
    }

    @PostMapping(value = "/add")
    public String addCountry(@ModelAttribute("country") @Valid Country country, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add_country";
        }
        try {
            service.addCountry(country);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "redirect:/admin/countries/add";
    }

    @GetMapping(value = "/del")
    public String delCountry(@RequestParam("id") int id) {
        try {
            service.deleteCountryById(id);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/admin/countries/add";
    }
}
