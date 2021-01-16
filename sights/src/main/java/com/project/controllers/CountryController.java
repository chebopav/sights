package com.project.controllers;

import com.project.entity.data.address.Country;
import com.project.exceptions.DataException;
import com.project.repository.CountryRepository;
import com.project.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/country")
public class CountryController {
    private CountryService service;
    private CountryRepository repository;

    @Autowired
    public CountryController(CountryService service, CountryRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping(value = "/all")
    public String showAll(Model model, @RequestParam("page") int page, @RequestParam("size") int size){
        try {
            Page<Country> countriesPage = service.getPageOfCountries(page, size);
            model.addAttribute("countries", countriesPage);
        } catch (DataException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "all_countries";
    }

}
