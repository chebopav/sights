package com.project.repository;

import com.project.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query(value = "SELECT u FROM User u WHERE u.login = :login")
    public User getUserByLogin(@Param("login") String login);
}
*/

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String login);
}
