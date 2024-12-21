package com.example.repositories.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.entities.Dette;
import com.example.repositories.jpa.impl.DetteRepositoryJpaImpl;

public class DetteRepositoryJpa extends RepositoryJpaImpl<Dette> implements DetteRepositoryJpaImpl {


    public DetteRepositoryJpa() {
        super(Dette.class);
    }

    // Find all debts where montantRestant == 0 (Settled/Solder)
    @Override
    public List<Dette> findAllDetteSolder() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantRestant = 0 AND d.status = 'VALIDER'";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        return query.getResultList();
    }
    @Override
    public List<Dette> findAllDetteAnulerOuEnCour() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantVerser = 0 AND (d.status = 'ENCOURS' OR d.status = 'ANNULER')";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        return query.getResultList();
    }
    @Override
    public List<Dette> findAllDetteAnuler() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantVerser = 0 AND (d.status = 'ANNULER')";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        return query.getResultList();
    }
    @Override
    public List<Dette> findAllDetteEnCour() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantVerser = 0 AND (d.status = 'ENCOURS' )";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        return query.getResultList();
    }
    @Override
    public List<Dette> findAllDetteValider() {
        String jpql = "SELECT d FROM Dette d WHERE d.status = 'VALIDER' ";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        return query.getResultList();
    }
    

    // Find all debts where montantRestant != 0 (Unsettled/Non-Solder)
    @Override
    public List<Dette> findAllDetteNonSolder() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantRestant <> 0 AND d.status = 'VALIDER'";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        return query.getResultList();
    }

    // Assuming you want to search Dette by another parameter (if needed)
    public Dette findById(Integer id) {
        try {
            // Query for finding a Dette by its ID
            return em.createQuery("SELECT d FROM Dette d WHERE d.id = :id", Dette.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no Dette is found
        }
    }

    @Override
    public List<Dette> findAllDetteByClientId(Integer clientId) {
        String jpql = "SELECT d FROM Dette d WHERE d.client.id = :clientId AND d.status = 'VALIDER'";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

    @Override
    public double sumMontantByClientId(Integer clientId) {
        List<Dette> dettes = findAllDetteByClientId(clientId);
        return dettes.stream()
                     .mapToDouble(Dette::getMontant) // Assuming getMontant() returns a double
                     .sum();
    }

    @Override
    public List<Dette> findAllDetteAnulerOuEnCourById(Integer clientId) {
        String jpql = "SELECT d FROM Dette d WHERE d.client.id = :clientId AND (d.status = 'ENCOURS' OR d.status = 'ANNULER')";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

    @Override
    public List<Dette> findAllDetteAnulerById(Integer clientId) {
        String jpql = "SELECT d FROM Dette d WHERE d.client.id = :clientId AND d.status = 'ANNULER'";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }
    @Override
    public List<Dette> findAllDetteEncoursById(Integer clientId) {
        String jpql = "SELECT d FROM Dette d WHERE d.client.id = :clientId AND d.status = 'ENCOURS'";
        TypedQuery<Dette> query = em.createQuery(jpql, Dette.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

}
