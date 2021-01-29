package com.project.controllers.for_user;

import com.project.entity.data.Sight;
import com.project.exceptions.DataException;
import com.project.services.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/sights")
public class SightController {
    private SightService sightService;

    @Autowired
    public SightController(SightService sightService) {
        this.sightService = sightService;
    }

    public SightService getSightService() {
        return sightService;
    }

    @GetMapping(value = "/view")
    public String viewSight(Model model, @RequestParam("id") long id) {
        Sight sight;
        try {
            sight = sightService.getSightById(id).orElse(null);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        model.addAttribute("sight", sight);
        /*model.addAttribute("comments", commentService.getAllCommentsById("museum", id));*/
        return "sight_view";
    }
}
