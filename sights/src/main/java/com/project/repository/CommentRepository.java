package com.project.repository;

import com.project.entity.data.Comment;
import com.project.entity.data.address.City;
import com.project.entity.users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

    @Query(value = "SELECT c FROM Comment c WHERE c.user = :user")
    public List<Comment> getAllCommentsOfUser(@Param("user") User user);

    // Don't work
    @Query(value = "SELECT c FROM Comment c WHERE c.id = :id")
    public Comment getCommentByID(@Param("id") long id);
}
