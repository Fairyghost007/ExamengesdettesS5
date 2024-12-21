package com.example.repositories.jpa.impl;
import java.util.List;

import com.example.entities.Paiement;

public interface PaiementRepositoryJpaImpl {
    List<Paiement> searchPaiementsOfDetteById(Integer detteId);
}
