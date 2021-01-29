package com.project.controllers.for_user;

import com.project.entity.data.Comment;
import com.project.entity.data.Museum;
import com.project.exceptions.DataException;
import com.project.repository.CommentRepository;
import com.project.repository.MuseumRepository;
import com.project.services.CommentService;
import com.project.services.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;
    private CommentRepository commentRepository;
    private MuseumService museumService;
    private MuseumRepository museumRepository;

    @Autowired
    public CommentController(CommentService commentService,
                             CommentRepository commentRepository,
                             MuseumService museumService,
                             MuseumRepository museumRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
        this.museumService = museumService;
        this.museumRepository = museumRepository;
    }

    // Don't work
    @PostMapping(value = "/add")
    public String addComment(@ModelAttribute("comment") @Valid Comment comment,
                             BindingResult bindingResult,
                             @RequestParam(name = "museum_id") long museum_id, Model model) {
        if (bindingResult.hasErrors()) {
            try {
                model.addAttribute("museum", museumService.getMuseumById(museum_id));
                return "museum_view";
            } catch (DataException e) {
                e.printStackTrace();
            }
        }
        try {
            Museum museum = museumService.getMuseumById(museum_id).orElse(null);
            museum.getComments().add(comment);
            comment.getMuseums().add(museum);
            museumRepository.save(museum);
        } catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!commentService.saveComment(comment)){
            model.addAttribute("comments", commentRepository.findAll());
            model.addAttribute("museum", museumRepository.findById(museum_id));
            return "museum_view";
        }
        return "museum_view";
    }
}
