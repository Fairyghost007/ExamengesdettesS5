// package com.example.core.Factory2.Impl;

// import detteproject.core.View;
// import detteproject.core.Factory.FactoryViewInterface;
// import detteproject.entities.Client;
// import detteproject.entities.User;
// import detteproject.services.ClientService;
// import detteproject.services.UserService;
// import detteproject.views.ClientView;
// import detteproject.views.UserView;

// public class FactoryView<T> implements FactoryViewInterface<T> {

//     private final View<T> view;

//     public FactoryView(Class<T> clazz, ClientService clientService, UserService userService, UserView userView) {

//         if (Client.class.isAssignableFrom(clazz)) {
//             this.view = (View<T>) new ClientView(userView, clientService, userService);
//         } else if (User.class.isAssignableFrom(clazz)) {
//             this.view = (View<T>) new UserView(userService);
//         } else {
//             throw new IllegalArgumentException("Unsupported entity type: " + clazz.getName());
//         }
//     }

//     @Override
//     public View<T> createView() {
//         return view;
//     }
// }
