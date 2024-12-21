package com.example.services;

import java.util.List;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.core.config.service.impl.ServiceImpl;
import com.example.entities.Client;
import com.example.entities.Paiement;
import com.example.repositories.jpa.PaiementRepositoryJpa;

public class PaiementServiceImpl extends ServiceImpl<Paiement> {
    private RepositoryJpaImpl<Paiement> repositoryJpaImpl;
    private PaiementRepositoryJpa paiementRepositoryJpa;

    public PaiementServiceImpl(PaiementRepositoryJpa paiementRepositoryJpa) {
        this.repositoryJpaImpl= new RepositoryJpaImpl<>(Paiement.class);
        this.paiementRepositoryJpa = paiementRepositoryJpa;
        // this.clientRepositoryJpa = new ClientRepositoryJpa();
    }

    
    public void create(Paiement paiement) {
        repositoryJpaImpl.insert(paiement);
    }

    
    public List<Paiement> findAll() {
        return repositoryJpaImpl.selectAll();
    }


    public List<Paiement> findPaiementsOfDetteById(Integer detteId) {
        return paiementRepositoryJpa.searchPaiementsOfDetteById(detteId);
    }

    
}
