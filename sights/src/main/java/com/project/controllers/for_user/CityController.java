package com.project.controllers.for_user;

import com.project.entity.data.address.City;
import com.project.exceptions.DataException;
import com.project.services.CityService;
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
@RequestMapping("/cities")
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    public CityService getCityService() {
        return cityService;
    }

    @GetMapping(value = "/all")
    public String getAllCities(Model model, @RequestParam("page") int page, @RequestParam("size") int size){
        try {
            Page<City> cityPage = cityService.getPageOfCities(page, size);
            model.addAttribute("cities", cityPage);
        } catch (DataException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "all_cities";
    }
}
