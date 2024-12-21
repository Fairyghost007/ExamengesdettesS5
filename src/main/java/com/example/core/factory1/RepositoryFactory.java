package com.example.core.factory1;

import com.example.repositories.db.ClientRepositoryDbImpl;
import com.example.repositories.db.UserRepositoryDbImpl;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.repositories.jpa.DetailRepositoryJpa;
import com.example.repositories.jpa.DetteRepositoryJpa;
import com.example.repositories.jpa.PaiementRepositoryJpa;
import com.example.repositories.jpa.UserRepositoryJpa;
import com.example.repositories.jpa.impl.ClientRepositoryJpaImpl;

public class RepositoryFactory {

    private static ClientRepositoryDbImpl clientRepositoryDbImpl=null;
    private static UserRepositoryDbImpl userRepositoryDbImpl=null;
    private static ClientRepositoryJpa clientRepositoryJpa=null;
    private static UserRepositoryJpa userRepositoryJpa=null;
    private static ArticleRepositoryJpa articleRepositoryJpa=null;
    private static DetteRepositoryJpa detteRepositoryJpa=null;
    private static DetailRepositoryJpa detailRepositoryJpa=null;
    private static PaiementRepositoryJpa paiementRepositoryJpa=null;
    

    public static ClientRepositoryDbImpl createClientRepository() {
        if(clientRepositoryDbImpl == null){
            clientRepositoryDbImpl = new ClientRepositoryDbImpl();
        }
        return clientRepositoryDbImpl;
    }
    public static ClientRepositoryJpa createClientRepositoryJpa() {
        if(clientRepositoryJpa == null){
            clientRepositoryJpa = new ClientRepositoryJpa();
        }
        return clientRepositoryJpa;
    }
    public static ArticleRepositoryJpa createArticleRepositoryJpa() {
        if(articleRepositoryJpa == null){
            articleRepositoryJpa = new ArticleRepositoryJpa();
        }
        return articleRepositoryJpa;
    }

    public static UserRepositoryJpa createUserRepositoryJpa() {
        if(userRepositoryJpa == null){
            userRepositoryJpa = new UserRepositoryJpa();
        }
        return userRepositoryJpa;
    }
    public static DetteRepositoryJpa createDetteRepositoryJpa() {
        if(detteRepositoryJpa == null){
            detteRepositoryJpa = new DetteRepositoryJpa();
        }
        return detteRepositoryJpa;
    }
    public static DetailRepositoryJpa createDetailRepositoryJpa() {
        if(detailRepositoryJpa == null){
            detailRepositoryJpa = new DetailRepositoryJpa();
        }
        return detailRepositoryJpa;
    }
    public static PaiementRepositoryJpa createPaiementRepositoryJpa() {
        if(paiementRepositoryJpa == null){
            paiementRepositoryJpa = new PaiementRepositoryJpa();
        }
        return paiementRepositoryJpa;
    }


    public static UserRepositoryDbImpl createUserRepository() {
        if(userRepositoryDbImpl == null){
            userRepositoryDbImpl = new UserRepositoryDbImpl();
        }
        return userRepositoryDbImpl;
    }


}
