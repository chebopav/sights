package com.project.services;

import com.project.entity.users.Role;
import com.project.entity.users.User;
import com.project.exceptions.DataException;
import com.project.repository.RoleRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public void setUserRepository(UserRepository repository) {
        this.repository = repository;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

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

    public User getUserByLogin(String login) throws DataException {
        User result = repository.getUserByLogin(login);
        if (result == null)
            throw new DataException("User не найден");
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repository.getUserByLogin(login);
        if (user == null){
            throw new UsernameNotFoundException("Пользователь не был найден");
        }
        return user;
    }

    public boolean saveUser(User user){
        User loaded =
                repository.getUserByLogin(user.getLogin());
        if (loaded != null){
            return false;
        }

        Role role = roleRepository.findById(1L).get();
        user.getRoles().add(role);
        user.setPassword(encoder.encode(user.getPassword()));
        role.getUsers().add(user);
        repository.save(user);
        return true;
    }
}
