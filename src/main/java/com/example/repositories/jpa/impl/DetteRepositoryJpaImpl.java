package com.example.repositories.jpa.impl;

import java.util.List;

import com.example.entities.Dette;

public interface DetteRepositoryJpaImpl {

    List<Dette> findAllDetteSolder();
    List<Dette> findAllDetteNonSolder();
    List <Dette> findAllDetteAnulerOuEnCour();
    List <Dette> findAllDetteAnulerOuEnCourById(Integer clientId);
    List <Dette> findAllDetteAnulerById(Integer clientId);
    List <Dette> findAllDetteByClientId(Integer clientId);
    List <Dette> findAllDetteValider();
    double sumMontantByClientId(Integer clientId);
    List<Dette> findAllDetteAnuler();
    List<Dette> findAllDetteEnCour();
    List<Dette> findAllDetteEncoursById(Integer clientId);

}
