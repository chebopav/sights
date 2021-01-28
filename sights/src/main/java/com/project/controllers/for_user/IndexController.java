package com.project.controllers.for_user;

import com.project.repository.CityRepository;
import com.project.repository.CountryRepository;
import com.project.services.ExcursionService;
import com.project.services.MuseumService;
import com.project.services.SightService;
import com.project.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    private CountryRepository countryRepository;
    private CityRepository cityRepository;
    private MuseumService museumService;
    private SightService sightService;
    private ExcursionService excursionService;
    private TheaterService theaterService;

    @Autowired
    public IndexController(CountryRepository countryRepository,
                           CityRepository cityRepository,
                           MuseumService museumService,
                           SightService sightService,
                           ExcursionService excursionService,
                           TheaterService theaterService) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.museumService = museumService;
        this.sightService = sightService;
        this.excursionService = excursionService;
        this.theaterService = theaterService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/list")
    public String listMuseum(Model model, @RequestParam("cityId") int cityId, @RequestParam("placeId") String placeId) {
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

}
