package com.example.views;

import java.util.List;
import java.util.Scanner;
import com.example.core.config.service.Service;
import com.example.core.config.view.impl.ViewImpl;
import com.example.entities.Client;
import com.example.entities.User;
import com.example.enums.Role;
import com.example.repositories.jpa.UserRepositoryJpa;
import com.example.services.ClientServiceImpl;
import com.example.services.UserServiceImpl;

public class UserViewImpl extends ViewImpl<User>{

    private Service<User> userServiceImpl;
    public  UserViewImpl(Service<User> userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public User saisie() {
        User user = new User();
        System.out.println("Enter email: ");
        user.setEmail(scanner.nextLine());
        System.out.println("Enter login: ");
        user.setLogin(scanner.nextLine());
        System.out.println("Enter password: ");
        user.setPassword(scanner.nextLine());
        System.out.println("Select a role:");
        Role[] roles = Role.values();
        for (int i = 0; i < roles.length-1; i++) {
            System.out.println(i + 1 + ". " + roles[i]);
        }
        int roleChoice;
        do {
            System.out.print("Enter the number corresponding to the role: ");
            roleChoice = scanner.nextInt();
            scanner.nextLine(); 
        } while (roleChoice < 1 || roleChoice > roles.length);
        user.setRole(roles[roleChoice - 1]);
        user.setStatut(true);
        return user;
    }


    public User findUserByLogin(UserServiceImpl userServiceImpl) {
        System.out.println("Enter login: ");
        String login = scanner.nextLine();
        User user = userServiceImpl.search(login);
        if (user != null) {
            affiche(user);
        } else {
            System.out.println("No client found with phone:"+user);
        }
        return user;
    }

    public void changeUserStatus() {
        UserServiceImpl userServiceImpl = new UserServiceImpl( new UserRepositoryJpa());
        // Fetch all users
        List<User> users = userServiceImpl.findAll();
    
        // Check if there are any users to display
        if (users.isEmpty()) {
            System.out.println("No users available.");
            return;
        }
    
        // Display all users
        System.out.println("List of Users:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Login: " + user.getLogin() + 
                               ", Email: " + user.getEmail() + ", Status: " + (user.isStatut() ? "Active" : "Inactive"));
        }
    
        Integer chosenUserId;
        Scanner scanner = new Scanner(System.in);
    
        // Prompt the user to choose a user by ID
        do {
            System.out.print("Enter the ID of the user you want to toggle status for: ");
            chosenUserId = scanner.nextInt();
    
            // Check if the entered ID exists directly in the loop
            if (userServiceImpl.getById(chosenUserId) == null) {
                System.out.println("Invalid ID. Please try again.");
            }
        } while (userServiceImpl.getById(chosenUserId) == null); // Keep prompting until a valid ID is entered
    
        // Fetch the user by ID
        User userToToggle = userServiceImpl.getById(chosenUserId);
    
        // Toggle the user's status
        userToToggle.setStatut(!userToToggle.isStatut());
        
        // Save the updated user back to the database
        userServiceImpl.update(userToToggle); // Assuming you have an update method in UserServiceImpl
    
        // Confirm the status change
        System.out.println("User status updated to: " + (userToToggle.isStatut() ? "Active" : "Desactive"));
    }
    public User connexion() {
        UserServiceImpl userServiceImpl = new UserServiceImpl(new UserRepositoryJpa());
        User user = null;
        do {
            System.out.println("Enter login: ");
            String login = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            
            user = userServiceImpl.findUserByLogin(login);
            
            if (user == null ) {  
                System.out.println("Invalid login or password. Please try again.");
                user = null; 
            }
        } while (user == null);
        
        System.out.println("Connexion réussie");
        return user;
    }
    
        
}
