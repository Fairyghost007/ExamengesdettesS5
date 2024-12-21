package com.example.services;

import java.util.List;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.core.config.service.impl.ServiceImpl;
import com.example.entities.Dette;
import com.example.repositories.jpa.DetteRepositoryJpa;


public class DetteServiceImpl    extends ServiceImpl<Dette>{

    private RepositoryJpaImpl<Dette> repositoryJpaImpl;
    private DetteRepositoryJpa detteRepositoryJpa;

    public DetteServiceImpl(DetteRepositoryJpa detteRepositoryJpa) {
        this.repositoryJpaImpl= new RepositoryJpaImpl<>(Dette.class);
        this.detteRepositoryJpa = new DetteRepositoryJpa();
    }

    public void create(Dette dette) {
        repositoryJpaImpl.insert(dette);
    }

    public List<Dette> findAll() {
        return repositoryJpaImpl.selectAll();
    }
    public void update(Dette dette) {
        repositoryJpaImpl.update(dette);
    }   
    public Dette getById(Integer id) {
        return repositoryJpaImpl.selectById(id);
    }

    public List<Dette> findAllDetteSolder() {
        return detteRepositoryJpa.findAllDetteSolder();
    }

    public List<Dette> findAllDetteNonSolder() {
        return detteRepositoryJpa.findAllDetteNonSolder();
    }

    public List<Dette> findAllDetteAnulerOuEnCour() {
        return detteRepositoryJpa.findAllDetteAnulerOuEnCour();
    }
    public List<Dette> findAllDetteAnuler() {
        return detteRepositoryJpa.findAllDetteAnuler();
    }
    public List<Dette> findAllDetteEnCour() {
        return detteRepositoryJpa.findAllDetteEnCour();
    }

     public List<Dette> findAllDetteByClientId(Integer clientId){
        return detteRepositoryJpa.findAllDetteByClientId(clientId);
     }
     public double sumMontantByClientId(Integer clientId){
         return detteRepositoryJpa.sumMontantByClientId(clientId);
     }
     public List<Dette> findAllDetteAnulerOuEnCourById(Integer clientId){
          return detteRepositoryJpa.findAllDetteAnulerOuEnCourById(clientId);
     }
     public List<Dette> findAllDetteValider(){
         return detteRepositoryJpa.findAllDetteValider();
     }
     public List<Dette> findAllDetteAnulerById(Integer clientId){
         return detteRepositoryJpa.findAllDetteAnulerById(clientId);
     }

     public List<Dette> findAllDetteEncoursById(Integer clientId){
         return detteRepositoryJpa.findAllDetteEncoursById(clientId);
     }
        
    public boolean delete(int id) {
        repositoryJpaImpl.delete(   id);
        return true;
    }

    
}
