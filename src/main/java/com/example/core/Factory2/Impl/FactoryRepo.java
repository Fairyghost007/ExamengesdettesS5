// package com.example.core.Factory2.Impl;

// import com.example.Repositorie.RepositorieClient;
// import detteproject.core.RepositoryBdImpl;
// import detteproject.core.Config.Repositorie;
// import detteproject.core.Factory.FactoryRepositoryInterface;
// import detteproject.entities.Client;
// import detteproject.entities.User;
// import detteproject.Repository.jpa.RepositoryJpaClient;
// import detteproject.Repository.jpa.RepositoryJpaUser;

// public class FactoryRepo<T> implements FactoryRepositoryInterface<T> {
//     private final Repositorie<T> repositorie;

//     public FactoryRepo(Class<T> clazz) {
//         if (Client.class.isAssignableFrom(clazz)) {
//             this.repositorie = (Repositorie<T>) new RepositoryJpaClient();
//         } else if (User.class.isAssignableFrom(clazz)) {
//             this.repositorie = (Repositorie<T>) new RepositoryJpaUser();
//         } else {

//             this.repositorie = (Repositorie<T>) new RepositoryJpaUser();
//         }
//     }

//     @Override
//     public Repositorie<T> createRepository() {
//         return repositorie;
//     }

// }
