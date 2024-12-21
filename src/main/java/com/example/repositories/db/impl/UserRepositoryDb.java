package com.example.repositories.db.impl;

import com.example.entities.Client;
import com.example.entities.User;

public interface UserRepositoryDb {
    User selectByLogin(String login);
    User selectById(int id);

}
