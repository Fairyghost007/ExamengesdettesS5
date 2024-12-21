package com.example.repositories.jpa;

import javax.persistence.TypedQuery;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.entities.Detail;
import com.example.entities.User;
import java.util.List;
import com.example.repositories.jpa.impl.DetailRepositoryJpaImpl;


public class DetailRepositoryJpa  extends RepositoryJpaImpl<Detail>  implements DetailRepositoryJpaImpl {

    public DetailRepositoryJpa() {
         super(Detail.class);
    }

    public List<Detail> searchDetailsOfDetteById(Integer detteId) {
        String jpql = "SELECT d FROM Detail d WHERE d.dette.id = :detteId";
        TypedQuery<Detail> query = em.createQuery(jpql, Detail.class);
        query.setParameter("detteId", detteId);
        return query.getResultList();
    }

    @Override
    public List<Detail> findAllDetailsByDetteId(Integer detteId) {
        String jpql = "SELECT d FROM Detail d WHERE d.dette.id = :detteId";
        TypedQuery<Detail> query = em.createQuery(jpql, Detail.class);
        query.setParameter("detteId", detteId);
        return query.getResultList();
    }
    
}
