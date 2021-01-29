package com.project.controllers.for_admin;

import com.project.entity.data.Museum;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.CountryRepository;
import com.project.repository.MuseumRepository;
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
@RequestMapping("/admin/museums")
public class MuseumAdminController {
    private CityRepository cityRepository;
    private MuseumService museumService;
    private MuseumRepository museumRepository;
    private CountryRepository countryRepository;

    @Autowired
    public MuseumAdminController(CityRepository cityRepository,
                                 MuseumRepository museumRepository,
                                 MuseumService museumService,
                                 CountryRepository countryRepository) {
        this.museumService = museumService;
        this.cityRepository = cityRepository;
        this.museumRepository = museumRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping(value = "/add")
    public String showForm(Model model){
        model.addAttribute("museum", new Museum());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("museums", museumRepository.findAll());
        return "add_museum";
    }

    @PostMapping(value = "/add")
    public String addMuseum(@ModelAttribute("museum") @Valid Museum museum,
                          BindingResult bindingResult,
                          @RequestParam(name = "cityId") int city_id, Model model) {
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
        return "redirect:/admin/museums/add";
    }

    @GetMapping(value = "/del")
    public String delMuseum(@RequestParam("id") int id) {
        try {
            museumService.deleteMuseumById(id);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/admin/museums/add";
    }
}
