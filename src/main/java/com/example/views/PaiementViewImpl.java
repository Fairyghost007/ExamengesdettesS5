package com.example.views;

import java.util.List;
import java.util.Scanner;

import com.example.core.config.service.Service;
import com.example.core.config.view.impl.ViewImpl;
import com.example.entities.Dette;
import com.example.entities.Paiement;
import com.example.repositories.jpa.DetteRepositoryJpa;
import com.example.repositories.jpa.PaiementRepositoryJpa;
import com.example.services.DetteServiceImpl;
import com.example.services.PaiementServiceImpl;

public class PaiementViewImpl extends ViewImpl {
    private Service<Paiement> paiementServiceImpl;
    private Service<Dette> detteServiceImpl;

    public PaiementViewImpl(Service<Paiement> paiementServiceImpl, Service<Dette> detteServiceImpl) {
        this.paiementServiceImpl = paiementServiceImpl;
        this.detteServiceImpl = detteServiceImpl;
    }

    @Override
public Paiement saisie() {
    DetteServiceImpl detteServiceImpl = new DetteServiceImpl(new DetteRepositoryJpa());
    PaiementServiceImpl paiementServiceImpl = new PaiementServiceImpl(new PaiementRepositoryJpa());
    Scanner scanner = new Scanner(System.in);
    
    // Display existing debts and select one
    Dette dette = selectDette();
    if (dette == null) {
        System.out.println("No Dette selected.");
        return null;
    }
    
    // Display the remaining amount of the Dette
    double montantRestant = dette.getMontantRestant();
    System.out.println("Montant Restant for this Dette: " + montantRestant);
    
    // Prompt user for payment details
    double paymentAmount;
    do {
        System.out.print("Enter payment amount: ");
        paymentAmount = scanner.nextDouble();
        
        if (paymentAmount > montantRestant) {
            System.out.println("Error: Payment amount exceeds the remaining debt (" + montantRestant + "). Please enter a valid amount.");
        }
    } while (paymentAmount > montantRestant); // Continue prompting until a valid amount is entered
    
    // Create a new payment and associate it with the selected Dette
    Paiement paiement = new Paiement();
    paiement.setMontant(paymentAmount);
    paiement.setDette(dette);  // Associate payment with the selected Dette
    
    // Save the payment
    paiementServiceImpl.create(paiement);
    
    // Update montantVerser and montantRestant in Dette
    double newMontantVerser = dette.getMontantVerser() + paymentAmount;
    dette.setMontantVerser(newMontantVerser);
    
    double newMontantRestant = dette.getMontantRestant() - paymentAmount;
    dette.setMontantRestant(newMontantRestant);
    
    // Save the updated Dette
    detteServiceImpl.update(dette);
    
    System.out.println("Payment of " + paymentAmount + " added to Dette with ID: " + dette.getId());
    
    return paiement;  // Return the created payment
}

    private Dette selectDette() {
        DetteServiceImpl detteServiceImpl = new DetteServiceImpl(new DetteRepositoryJpa());
        List<Dette> dettes = detteServiceImpl.findAllDetteValider();
        Scanner scanner = new Scanner(System.in);
        
        // Display existing Dettes
        System.out.println("List of Dettes:");
        for (Dette dette : dettes) {
            System.out.println("ID: " + dette.getId() + ", Montant: " + dette.getMontant() +
                               ", Montant Verser: " + dette.getMontantVerser() +
                               ", Montant Restant: " + dette.getMontantRestant() +
                               ", Client ID: " + (dette.getClient() != null ? dette.getClient().getId() : "null"));
        }
        
        System.out.print("Enter the ID of the Dette to add a payment: ");
        Integer detteId = scanner.nextInt();
        
        return detteServiceImpl.getById(detteId);  // Return the selected Dette
    }
}
