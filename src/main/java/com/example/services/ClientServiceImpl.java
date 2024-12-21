package com.example.services;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.core.config.service.impl.ServiceImpl;
import com.example.entities.Client;
import com.example.repositories.jpa.ClientRepositoryJpa;
import java.util.List;


public class ClientServiceImpl extends ServiceImpl<Client> {
    private RepositoryJpaImpl<Client> repositoryJpaImpl;
    private ClientRepositoryJpa clientRepositoryJpa;

    public ClientServiceImpl(ClientRepositoryJpa clientRepositoryJpa) {
        this.repositoryJpaImpl= new RepositoryJpaImpl<>(Client.class);
        this.clientRepositoryJpa = clientRepositoryJpa;
        // this.clientRepositoryJpa = new ClientRepositoryJpa();
    }

    public void create(Client client) {
        repositoryJpaImpl.insert(client);
    }

    public List<Client> findAll() {
        return repositoryJpaImpl.selectAll();
    }

    public Client search(String phone) {
        return clientRepositoryJpa.selectByPhone(phone);
    }

    public void update(Client client) {
        repositoryJpaImpl.update(client);
    }
    public List<Client> findAllClientNull(){
        return clientRepositoryJpa.findAllClientNull();
    }
    public Client getById(int id) {
        return repositoryJpaImpl.selectById(id);
    }
    public Client findClientByUserId(Integer id) {
        return clientRepositoryJpa.findClientByUserId(id);
    }
}
