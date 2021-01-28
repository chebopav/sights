package com.project.controllers.for_user;

import com.project.entity.data.Excursion;
import com.project.exceptions.DataException;
import com.project.services.ExcursionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/excursions")
public class ExcursionController {

    private ExcursionService excursionService;

    @Autowired
    public ExcursionController(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }

    public ExcursionService getExcursionService() {
        return excursionService;
    }

    @GetMapping(value = "/view")
    public String viewExcursion(Model model, @RequestParam("id") long id) {
        Excursion excursion;
        try {
            excursion = excursionService.getExcursionById(id).orElse(null);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        model.addAttribute("excursion", excursion);
        /*model.addAttribute("comments", commentService.getAllCommentsById("museum", id));*/
        return "excursion_view";
    }
}
