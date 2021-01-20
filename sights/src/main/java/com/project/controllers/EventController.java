package com.project.controllers;

import com.project.entity.afisha.Event;
import com.project.entity.data.NeedDate;
import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.services.CityService;
import com.project.services.EventService;
import com.project.services.NeedDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Controller(value = "/event")
public class EventController {
    private EventService eventService;
    private ApplicationContext context;

    @Autowired
    public EventController(EventService eventService, ApplicationContext context) {
        this.eventService = eventService;
        this.context = context;
    }

    @GetMapping(value = "/random")
    public String showRandomEventOfCityAndDate(Model model, @RequestParam("city_id") int cityId, @RequestParam("date") String date){
        String[] forDate = date.split("\\.");
        int day = Integer.parseInt(forDate[0]);
        int month = Integer.parseInt(forDate[1]);
        int year = Integer.parseInt(forDate[2]);
        LocalDate gotDate = LocalDate.of(year, month, day);
        City city;
        NeedDate needDate;
        try {
            city = context.getBean(CityService.class).getCityById(cityId).get();
            needDate = context.getBean(NeedDateService.class).getNeedDateByDate(gotDate);

        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        List<Event> events = eventService.getAllEventsToDateInCity(needDate, city);
        Event random = events.get((int)(Math.random() * events.size()));
        model.addAttribute("event", random);
        return "random_event";
    }
}
