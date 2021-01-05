package com.project.services;

import com.project.entity.users.User;
import com.project.exceptions.DataException;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserRepository getRepository() {
        return repository;
    }

    public User addUser(User user) throws DataException {
        if(repository.existsById(user.getId())){
            throw new DataException("User уже существует");
        }
        return repository.save(user);
    }

    public User updateUser(User user) throws DataException {
        if(!repository.existsById(user.getId())){
            throw new DataException("User не существует");
        }
        return repository.save(user);
    }

    public Page<User> getPageOfUsers(int page, int size) throws DataException {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = repository.findAll(pageable);
        if (userPage.isEmpty()){
            throw new DataException("Записи User не найдены");
        }
        return userPage;
    }

    public Optional<User> getUserById(long id) throws DataException {
        Optional<User> result = repository.findById(id);
        if (result.isEmpty()){
            throw new DataException("User не найден");
        }
        return result;
    }

    public void deleteUserById(long id) throws DataException {
        if (!repository.existsById(id)){
            throw new DataException("User не найден");
        }
        repository.deleteById(id);
    }
}
