package com.project.controllers.for_user;

import com.project.entity.afisha.Event;
import com.project.entity.data.Excursion;
import com.project.entity.data.Museum;
import com.project.entity.data.NeedDate;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.helpers_and_statics.Statics;
import com.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Controller
@RequestMapping("/program")
public class ProgramController {
    private EventService eventService;
    private MuseumService museumService;
    private ExcursionService excursionService;
    private ApplicationContext context;

    @Autowired
    public ProgramController(EventService eventService, MuseumService museumService, ExcursionService excursionService, ApplicationContext context) {
        this.eventService = eventService;
        this.museumService = museumService;
        this.excursionService = excursionService;
        this.context = context;
    }

    @GetMapping(value = "/")
    public String showRandomEventOfCityAndDate(Model model, @RequestParam("city_id") int cityId, @RequestParam("date") String date){
        LocalDate gotDate = Statics.makeDateFromString(date);
        City city;
        NeedDate needDate;
        try {
            city = context.getBean(CityService.class).getCityById(cityId).get();
            needDate = context.getBean(NeedDateService.class).getNeedDateByDate(gotDate);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        if (Math.random() >= 0.5) {
            Event randomEvent = eventService.getRandomEventToDateInCity(needDate, city);
            model.addAttribute("event", randomEvent);
        } else {
            Excursion randomExcursion = excursionService.getRandomEventToDateInCity(needDate, city);
            model.addAttribute("event", randomExcursion);
        }

        Museum randomMuseum = museumService.getRandomMuseumOnCity(city);
        model.addAttribute("museum", randomMuseum);

        return "random_program";
    }
}
