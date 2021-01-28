package com.project.controllers;

import com.project.entity.data.Museum;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
import com.project.repository.MuseumRepository;
import com.project.services.CityService;
import com.project.services.MuseumService;
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

    @Autowired
    public MuseumController(CityService cityService,
                            CityRepository cityRepository,
                            MuseumRepository museumRepository,
                            MuseumService museumService) {
        this.cityService = cityService;
        this.museumService = museumService;
        this.cityRepository = cityRepository;
        this.museumRepository = museumRepository;
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
}
