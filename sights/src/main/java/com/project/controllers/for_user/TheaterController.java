package com.project.controllers.for_user;

import com.project.entity.data.Theater;
import com.project.exceptions.DataException;
import com.project.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/theaters")
public class TheaterController {

    private TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    public TheaterService getTheaterService() {
        return theaterService;
    }

    @GetMapping(value = "/view")
    public String viewTheater(Model model, @RequestParam("id") long id) {
        Theater theater;
        try {
            theater = theaterService.getTheaterById(id).orElse(null);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        model.addAttribute("theater", theater);
        /*model.addAttribute("comments", commentService.getAllCommentsById("museum", id));*/
        return "theater_view";
    }
}
