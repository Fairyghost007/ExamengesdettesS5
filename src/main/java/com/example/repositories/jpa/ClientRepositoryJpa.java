package com.example.repositories.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.repositories.jpa.impl.ClientRepositoryJpaImpl;

import com.example.entities.Client;


public class ClientRepositoryJpa extends RepositoryJpaImpl<Client>  implements ClientRepositoryJpaImpl {

    public ClientRepositoryJpa() {
        super(Client.class); 
    }

    @Override
    public Client selectByPhone(String phone) {
        try {
            String jpql = "SELECT c FROM Client c WHERE c.phone = :phone";
            TypedQuery<Client> query = em.createQuery(jpql, Client.class); 
            query.setParameter("phone", phone);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    @Override
    public List<Client> findAllClientNull() {
        String jpql = "SELECT c FROM Client c WHERE c.user IS NULL";
        TypedQuery<Client> query = em.createQuery(jpql, Client.class); 
        return query.getResultList();
    }

    @Override
    public Client findClientByUserId(Integer id) {
        String jpql = "SELECT c FROM Client c WHERE c.user.id = :id";
        TypedQuery<Client> query = em.createQuery(jpql, Client.class); 
        query.setParameter("id", id);
        return query.getSingleResult();
    }


}
