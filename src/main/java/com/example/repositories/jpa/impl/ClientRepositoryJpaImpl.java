package com.example.repositories.jpa.impl;

import com.example.entities.Client;
import java.util.List;


public interface ClientRepositoryJpaImpl {
     Client selectByPhone(String phone);
     List<Client> findAllClientNull();
     Client findClientByUserId(Integer id);
}
