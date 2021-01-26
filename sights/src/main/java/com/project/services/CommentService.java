package com.project.services;

import com.project.entity.data.Comment;
import com.project.entity.users.User;
import com.project.exceptions.DataException;
import com.project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository repository;

    @Autowired
    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public CommentRepository getRepository() {
        return repository;
    }

    public Comment addComment(Comment comment) throws DataException {
        return repository.save(comment);
    }

    public Comment updateComment(Comment comment) throws DataException {
        if(!repository.existsById(comment.getId())){
            throw new DataException("Комментарий не существует");
        }
        return repository.save(comment);
    }

    public Page<Comment> getPageOfComments(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> cityPage = repository.findAll(pageable);
        if (cityPage.isEmpty()){
            throw new DataException("Комментарии не найдены");
        }
        return cityPage;
    }

    public Optional<Comment> getCommentById(long id) throws DataException {
        Optional<Comment> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("Комментарий не найден");
        }
        return result;
    }

    public void deleteCommentById(long id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("Комментарий не найден");
        }
        repository.deleteById(id);
    }

    public List<Comment> getAllCommentsOfUser(User user){
        return null;
    }

}
