package com.example.views;

import java.util.List;
import java.util.Scanner;

import com.example.services.ArticleServiceImpl;
import com.example.services.ClientServiceImpl;
import com.example.services.DetailServiceImpl;
import com.example.services.DetteServiceImpl;
import com.example.services.UserServiceImpl;
import com.example.core.config.service.Service;
import com.example.core.config.view.impl.ViewImpl;
import com.example.entities.Article;
import com.example.entities.Client;
import com.example.entities.Detail;
import com.example.entities.User;
import com.example.entities.Dette;
import com.example.entities.Paiement;
import com.example.enums.Role;
import com.example.enums.Statut;
import com.example.repositories.db.UserRepositoryDbImpl;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.repositories.jpa.DetailRepositoryJpa;
import com.example.repositories.jpa.DetteRepositoryJpa;
import com.example.repositories.jpa.UserRepositoryJpa;

public class ClientViewImpl extends ViewImpl<Client>{

    private Service<Client> clientServiceImpl;
    private Service<User> userServiceImpl;
    public  ClientViewImpl(Service<Client> clientServiceImpl, Service<User> userServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }


    @Override
    public Client saisie() {
        Client client = new Client();
        UserServiceImpl userServiceImpl = new UserServiceImpl(new UserRepositoryJpa());
        
        System.out.println("Enter surname: ");
        client.setSurname(scanner.nextLine());
        
        System.out.println("Enter phone: ");
        client.setPhone(scanner.nextLine());
        
        System.out.println("Enter address: ");
        client.setAddress(scanner.nextLine());

        handleUserAccountSelection( client);
        
        return client;
    }
    private void handleUserAccountSelection(Client client) {
        int choice;
    
        do {
            System.out.println("1- Create a user account\n2- Finish");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            if (choice == 1) {
                User newUser = new User();
                System.out.println("Enter email: ");
                newUser.setEmail(scanner.nextLine());
                System.out.println("Enter login: ");
                newUser.setLogin(scanner.nextLine());
                System.out.println("Enter password: ");
                newUser.setPassword(scanner.nextLine());
                newUser.setRole(Role.CLIENT);
                newUser.setStatut(true);
                client.setUser(newUser);
                System.out.println("New user account created with role CLIENT.");
                break; 
            }
            if (choice == 2) {
                System.out.println("Finishing the operation.");
                break;  
            }
            if (choice < 1 || choice > 2) {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        } while (true);  
    }

    public void createUserForClient(){
        UserServiceImpl userServiceImpl = new UserServiceImpl(new UserRepositoryJpa());
        ClientServiceImpl clientService = new ClientServiceImpl(new ClientRepositoryJpa());
        List<Client> clients=clientService.findAllClientNull();
        for (Client client : clients) {
            System.out.println("ID: " + client.getId() + ", Surname: " + client.getSurname());
        }
        Integer chosenClientId;
        // Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Enter the ID of the Client you want to look for: ");
            chosenClientId = scanner.nextInt();
    
            // Check if the entered ID exists directly in the loop
            if (clientService.getById(chosenClientId) == null) {
                System.out.println("Invalid ID. Please try again.");
            }
        } while (clientService.getById(chosenClientId) == null);

        Client client = clientService.getById(chosenClientId);
        scanner.nextLine();
        User newUser = new User();
        System.out.println("Enter email: ");
        newUser.setEmail(scanner.nextLine());
        System.out.println("Enter login: ");
        newUser.setLogin(scanner.nextLine());
        System.out.println("Enter password: ");
        newUser.setPassword(scanner.nextLine());
        newUser.setRole(Role.CLIENT);
        newUser.setStatut(true);
        userServiceImpl.create(newUser);
        client.setUser(newUser);
        clientService.update(client);
        

    }
    
    public void afficheClientByPhone(ClientServiceImpl clientService) {
        System.out.println("Enter telephone: ");
        String phone = scanner.nextLine();
        Client client = clientService.search(phone);
        if (client != null) {
            affiche(client);
        } else {
            System.out.println("No client found with phone:"+phone);
        }
    }

    public void listAllDetteOfClient(Integer chosenClientId){
        // ClientServiceImpl clientService = new ClientServiceImpl(new ClientRepositoryJpa());
        DetteServiceImpl detteService= new DetteServiceImpl(new DetteRepositoryJpa());
        DetailServiceImpl detailService = new DetailServiceImpl(new DetailRepositoryJpa());
        ArticleServiceImpl articleServiceImpl = new ArticleServiceImpl(new ArticleRepositoryJpa());


        List<Dette> dettes = detteService.findAllDetteByClientId(chosenClientId);
        for (Dette dette : dettes) {
            System.out.println("ID: " + dette.getId() +", Montant: " + dette.getMontant() + ",Montant Verser"+ dette.getMontantVerser()+
                               ", Montant Restant: " + dette.getMontantRestant()+ ", Statut:"+ dette.getStatus());
        }
        Integer chosenDette;
        // Scanner scanner = new Scanner(System.in);

        // Prompt the user to choose an article by ID
        do {
            System.out.print("Enter the ID of the Dette you want to look for: ");
            chosenDette = scanner.nextInt();

            // Check if the entered ID exists directly in the loop
            if (detteService.getById(chosenDette) == null) {
                System.out.println("Invalid ID. Please try again.");
            }
        } while (detteService.getById(chosenDette) == null); 
        Dette dette = detteService.getById(chosenDette);

        System.out.println("Choose an option:");
        System.out.println("1. Articles");
        System.out.println("2. Paiements");
        int choice;
        choice = scanner.nextInt();

        if (choice == 1) {
            List<Detail> details = detailService.findAllDetailsByDetteId(chosenDette);
            for (Detail detail : details) {
                Article article= articleServiceImpl.getById(detail.getArticle().getId());
                System.out.println( " Article: " + article.getLibelle() +
                                ", Quantite Demander: " + detail.getQteDette()+ ", Qantite en stock:"+article.getQteStock());
                
            }
        } else if (choice == 2) {
            List<Paiement> paiements = dette.getPaiements();
            for (Paiement paiement : paiements) {
                System.out.println("ID: " + paiement.getId() + ", Montant: " + paiement.getMontant());
            }
        }else{
            System.out.println("Merci");
        }
    }

    public void listerDemandeDeDette(Integer chosenClientId){
        DetteServiceImpl detteService= new DetteServiceImpl(new DetteRepositoryJpa());
        // Integer chosenClientId;
        // Scanner scanner = new Scanner(System.in);
        // List <Client> clients = clientService.findAll();
        // for (Client client : clients) {
        //     System.out.println("ID: " + client.getId() + ", Surname: " + client.getSurname());
        // }
        // do {
        //     System.out.print("Enter the ID of the Client you want to look for: ");
        //     chosenClientId = scanner.nextInt();
    
        //     // Check if the entered ID exists directly in the loop
        //     if (clientService.getById(chosenClientId) == null) {
        //         System.out.println("Invalid ID. Please try again.");
        //     }
        // } while (clientService.getById(chosenClientId) == null);

        List<Dette> dettes = detteService.findAllDetteAnulerOuEnCourById(chosenClientId);
        for (Dette dette : dettes) {
            System.out.println("ID: " + dette.getId() +", Montant: " + dette.getMontant() + ",Montant Verser"+ dette.getMontantVerser()+
                               ", Montant Restant: " + dette.getMontantRestant()+ ", Statut:"+ dette.getStatus());
        }


    }


    public void sendRelanceForDette(Integer chosenClientId){
        DetteServiceImpl detteService= new DetteServiceImpl(new DetteRepositoryJpa());

        List<Dette> dettes = detteService.findAllDetteAnulerById(chosenClientId);
        if (dettes.isEmpty()) {
            System.out.println("No dettes Anuler found for this client.");
            return;
        }
        for (Dette dette : dettes) {
            System.out.println("ID: " + dette.getId() +", Montant: " + dette.getMontant() + ",Montant Verser"+ dette.getMontantVerser()+
                               ", Montant Restant: " + dette.getMontantRestant()+ ", Statut:"+ dette.getStatus());
        }
        Integer chosenDette;

        // Prompt the user to choose an article by ID
        do {
            System.out.print("Enter the ID of the Dette you want to do a relance  for: ");
            chosenDette = scanner.nextInt();

            // Check if the entered ID exists directly in the loop
            if (detteService.getById(chosenDette) == null) {
                System.out.println("Invalid ID. Please try again.");
            }
        } while (detteService.getById(chosenDette) == null); 
        Dette dette = detteService.getById(chosenDette);
        dette.setStatus(Statut.ENCOURS);
        detteService.update(dette);
    }


}
