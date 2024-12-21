package com.example.repositories.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.entities.Paiement;
import com.example.entities.User;
import com.example.repositories.jpa.impl.PaiementRepositoryJpaImpl;

public class PaiementRepositoryJpa  extends RepositoryJpaImpl<Paiement>  implements PaiementRepositoryJpaImpl {

    public PaiementRepositoryJpa() {
         super(Paiement.class);
    }

    @Override
public List<Paiement> searchPaiementsOfDetteById(Integer detteId) {
    String jpql = "SELECT p FROM Paiement p WHERE p.dette.id = :detteId";

    TypedQuery<Paiement> query = em.createQuery(jpql, Paiement.class);
    query.setParameter("detteId", detteId);

    return query.getResultList();
}
    
}
