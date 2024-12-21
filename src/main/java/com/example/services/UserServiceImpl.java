package com.example.services;

import java.util.List;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.core.config.service.impl.ServiceImpl;
import com.example.entities.User;
import com.example.repositories.jpa.UserRepositoryJpa;

public class UserServiceImpl extends ServiceImpl<User>{
    private UserRepositoryJpa userRepositoryJpa;
    private RepositoryJpaImpl<User> repositoryJpaImpl;
    


    public UserServiceImpl(UserRepositoryJpa userRepositoryJpa) {
        this.userRepositoryJpa = new UserRepositoryJpa();
        this.repositoryJpaImpl = new RepositoryJpaImpl<>(User.class);
    }

    public void create(User user) {
        repositoryJpaImpl.insert(user);
    }

    public List<User> findAll() {
        return repositoryJpaImpl.selectAll();
    }

    public User search(String login) {
        return userRepositoryJpa.selectByLogin(login);
    }

    public User getById(int id) {
        return repositoryJpaImpl.selectById(id);
    }
     

    public User update(User user) {
        repositoryJpaImpl.update(user);
        return user;
    }

    public List<User> findAllUserActive() {
        return userRepositoryJpa.selectAllUserActive();
    }
    public List<User> findAllUserActiveAdmin() {
        return userRepositoryJpa.selectAllUserActiveAdmin();
    }
    public List<User> findAllUserActiveBoutiquier() {
        return userRepositoryJpa.selectAllUserActiveBoutiquier();
    }
    public User findUserByLogin(String login) {
        return userRepositoryJpa.selectByLogin(login);
    }
}