package com.example.core.factory1;
import com.example.core.factory1.RepositoryFactory;
import com.example.services.ClientServiceImpl;
import com.example.services.UserServiceImpl;
import com.example.repositories.jpa.impl.ClientRepositoryJpaImpl;



public class ServiceFactory {
    private static ClientServiceImpl clientServiceImpl=null;
    private static UserServiceImpl userServiceImpl=null;
    public static ClientServiceImpl createClientService() {
        if(clientServiceImpl == null){
            clientServiceImpl = new ClientServiceImpl(RepositoryFactory.createClientRepositoryJpa());
        }
        return clientServiceImpl;
    }

    public static UserServiceImpl createUserService() {
        if(userServiceImpl == null){
            userServiceImpl = new UserServiceImpl(RepositoryFactory.createUserRepositoryJpa());
        }
        return userServiceImpl;
    }
    
}
