package com.project.controllers.for_admin;

import com.project.entity.data.Museum;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.repository.CityRepository;
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
    private CityService cityService;
    private CityRepository cityRepository;
    private MuseumService museumService;
    private MuseumRepository museumRepository;
    private ExcursionService excursionService;
    private SightService sightService;
    private TheaterService theaterService;
    private CommentService commentService;

    @Autowired
    public MuseumAdminController(CityService cityService,
                                 CityRepository cityRepository,
                                 MuseumRepository museumRepository,
                                 MuseumService museumService,
                                 ExcursionService excursionService,
                                 TheaterService theaterService,
                                 SightService sightService,
                                 CommentService commentService) {
        this.cityService = cityService;
        this.museumService = museumService;
        this.cityRepository = cityRepository;
        this.museumRepository = museumRepository;
        this.excursionService = excursionService;
        this.sightService = sightService;
        this.theaterService = theaterService;
        this.commentService = commentService;
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
