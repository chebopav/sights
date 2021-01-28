package com.project.controllers.for_user;

import com.project.entity.data.Museum;
import com.project.exceptions.DataException;
import com.project.services.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/museums")
public class MuseumController {
    private MuseumService museumService;

    @Autowired
    public MuseumController(MuseumService museumService) {
        this.museumService = museumService;
    }

    public MuseumService getMuseumService() {
        return museumService;
    }

    @GetMapping(value = "/view")
    public String viewMuseum(Model model, @RequestParam("id") long id) {
        Museum museum;
        try {
            museum = museumService.getMuseumById(id).orElse(null);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        model.addAttribute("museum", museum);
        /*model.addAttribute("comments", commentService.getAllCommentsById("museum", id));*/
        return "museum_view";
    }
}
