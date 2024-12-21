package com.example.repositories.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.entities.User;
import com.example.repositories.jpa.impl.UserRepositoryJpaImpl;

public class UserRepositoryJpa extends RepositoryJpaImpl<User> implements UserRepositoryJpaImpl {

    public UserRepositoryJpa() {
        super(User.class);
    }

    // @Override
    // public User selectByLogin(String login) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'selectByLogin'");
    // }

    // @Override
    // public User selectByLogin(String login) {
    //     try {
    //         return this.em.createNamedQuery("User.selectByLogin", User.class)
    //                       .setParameter("login", login)
    //                       .getSingleResult();
    //     } catch (NoResultException e) {
    //         return null;  // Return null if no user is found instead of throwing an exception
    //     }
    // }
    @Override
public User selectByLogin(String login) {
    try {
        return this.em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                      .setParameter("login", login)
                      .getSingleResult();
    } catch (NoResultException e) {
        return null;  // Return null if no user is found instead of throwing an exception
    }
}

    

@Override
public List<User> selectAllUserActive() {
    String jpql = "SELECT u FROM User u WHERE u.statut = true";
    TypedQuery<User> query = em.createQuery(jpql, User.class);
    return query.getResultList();
}
@Override
public List<User> selectAllUserActiveAdmin() {
    String jpql = "SELECT u FROM User u WHERE u.statut = true AND u.role = 'ADMIN'";
    TypedQuery<User> query = em.createQuery(jpql, User.class);
    return query.getResultList();
}
@Override
public List<User> selectAllUserActiveBoutiquier() {
    String jpql = "SELECT u FROM User u WHERE u.statut = true AND u.role = 'BOUTIQUIER'";
    TypedQuery<User> query = em.createQuery(jpql, User.class);
    return query.getResultList();
}




}
