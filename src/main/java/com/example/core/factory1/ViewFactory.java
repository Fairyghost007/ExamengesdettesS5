package com.example.core.factory1;
import com.example.core.factory1.RepositoryFactory;
import com.example.core.factory1.ServiceFactory;
import com.example.views.ClientViewImpl;
import com.example.views.UserViewImpl;


public class ViewFactory {
    private static ClientViewImpl clientViewImpl=null;
    private static UserViewImpl userViewImpl=null;
    public static ClientViewImpl createClientService() {
        if(clientViewImpl == null){
            clientViewImpl = new ClientViewImpl(ServiceFactory.createClientService(),ServiceFactory.createUserService());
        }
        return clientViewImpl;
    }

    public static UserViewImpl createUserService() {
        if(userViewImpl == null){
            userViewImpl = new UserViewImpl(ServiceFactory.createUserService());
        }
        return userViewImpl;
    }
    
}
