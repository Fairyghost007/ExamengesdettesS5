package com.example.services;

import java.util.List;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.core.config.service.impl.ServiceImpl;
import com.example.entities.Client;
import com.example.entities.Detail;
import com.example.repositories.jpa.DetailRepositoryJpa;

public class DetailServiceImpl  extends ServiceImpl<Detail> {

    private RepositoryJpaImpl<Detail> repositoryJpaImpl;
    private DetailRepositoryJpa detailRepositoryJpa;

    public DetailServiceImpl(DetailRepositoryJpa clientRepositoryJpa) {
        this.repositoryJpaImpl= new RepositoryJpaImpl<>(Detail.class);
        this.detailRepositoryJpa = new DetailRepositoryJpa();
    }

    public void create(Detail detail) {
        repositoryJpaImpl.insert(detail);
    }

    public List<Detail> findAll() {
        return repositoryJpaImpl.selectAll();
    }
    public List<Detail> findDetailsOfDetteById(Integer detteId) {
        return detailRepositoryJpa.searchDetailsOfDetteById(detteId);
    }

    public List<Detail> findAllDetailsByDetteId(Integer detteId) {
        return detailRepositoryJpa.findAllDetailsByDetteId(detteId);
    }

    
}
