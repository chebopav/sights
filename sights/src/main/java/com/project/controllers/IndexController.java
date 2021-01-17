package com.project.controllers;

import com.project.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private CityRepository repository;

    @Autowired
    public IndexController(CityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }
}
