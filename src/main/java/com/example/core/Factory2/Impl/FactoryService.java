// package com.example.core.Factory2.Impl;

// import detteproject.Repository.jpa.RepositoryJpaClient;
// import detteproject.Repository.jpa.RepositoryJpaUser;
// import detteproject.core.RepositorieClient;
// import detteproject.core.RepositorieUser;
// import detteproject.core.Config.Repositorie;
// import detteproject.core.Config.Service;
// import detteproject.core.Factory.FactoryServiceInterface;
// import detteproject.entities.Client;
// import detteproject.entities.User;
// import detteproject.services.ClientService;
// import detteproject.services.UserService;

// public class FactoryService<T> implements FactoryServiceInterface<T> {
//     private final Service<T> service;
//     private final Repositorie<T> repositorie;

//     @SuppressWarnings("unchecked")
//     public FactoryService(Class<T> clazz, Repositorie<T> repositorie) {
//         this.repositorie = repositorie;
//         if (Client.class.isAssignableFrom(clazz)) {
//             this.service = (Service<T>) new ClientService((RepositoryJpaClient) repositorie); // Correct instantiation
//         } else if (User.class.isAssignableFrom(clazz)) {
//             this.service = (Service<T>) new UserService((RepositorieUser) repositorie); // Correct instantiation
//         } else {
//             throw new IllegalArgumentException("Unsupported entity type: " + clazz.getName());
//         }
//     }

//     @Override
//     public Service<T> createService() {
//         return service; // Return the created service
//     }
// }
