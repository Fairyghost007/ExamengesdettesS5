package com.example.repositories.db.impl;

import com.example.entities.Client;

public interface ClientRepositoryDb {
    Client selectByPhone(String phone);
}
