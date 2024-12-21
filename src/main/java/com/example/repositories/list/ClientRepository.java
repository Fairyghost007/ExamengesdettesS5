package com.example.repositories.list;

import com.example.core.config.repository.impl.list.RepositoryListImpl;
import com.example.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository extends RepositoryListImpl<Client> {
    // private final List<Client> clientList = new ArrayList<>();

    // public Client selectByPhone(String phone) {
    //     return clientList.stream().filter(client -> client.getPhone().compareTo(phone) == 0).findFirst().orElse(null);
    // }
}