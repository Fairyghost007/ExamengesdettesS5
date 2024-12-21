package com.example.repositories.jpa.impl;

import com.example.entities.User;
import java.util.List;

public interface UserRepositoryJpaImpl {
     
    
    User selectByLogin(String login);
    List<User> selectAllUserActive();
    List<User> selectAllUserActiveAdmin();
    List<User> selectAllUserActiveBoutiquier();

}
