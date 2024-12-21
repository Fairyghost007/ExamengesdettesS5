package com.example.repositories.db;

import com.example.core.config.repository.impl.db.RepositoryDbImpl;
import com.example.entities.Client;
import com.example.entities.User;
import com.example.repositories.db.impl.ClientRepositoryDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryDbImpl extends RepositoryDbImpl<Client> implements ClientRepositoryDb {
    UserRepositoryDbImpl userRepositoryDb =new UserRepositoryDbImpl();

    public ClientRepositoryDbImpl() {
        this.tableName = "client";
        this.userRepositoryDb =userRepositoryDb;
    }
    @Override
public void insert(Client client) {
    User user = client.getUser();
    try {
        if (user != null) {
            userRepositoryDb.insert(user);
        }

        String sql = String.format("INSERT INTO %s (surname, phone, address, \"userId\") VALUES (?, ?, ?, ?)", this.tableName);
        this.init(sql);
        this.ps.setString(1, client.getSurname());
        this.ps.setString(2, client.getPhone());
        this.ps.setString(3, client.getAddress());
        
        // Check if user is not null and set userId
        if (user != null && user.getId() > 0) {
            this.ps.setInt(4, user.getId());
        } else {
            this.ps.setNull(4, Types.INTEGER);
        }

        this.executeUpdate();
        ResultSet rs = this.ps.getGeneratedKeys();
        if (rs.next()) {
            client.setId(rs.getInt(1));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


    @Override
    public Client selectByPhone(String phone) {
        Client client = null;
        try{
            String sql=String.format("SELECT * FROM %s WHERE phone = ?", this.tableName);
            this.init(sql);
            this.ps.setString(1, sql);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
                client = this.convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                this.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return client;
    }
    @Override
    public List<Client> selectAll() {
        List<Client> clients = new ArrayList<>();
        try{
            String sql =String.format("SELECT * FROM %s", this.tableName);
            this.init(sql);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                clients.add( this.convertToObject(rs));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                this.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clients;
    }



    @Override
    public int insert(String sql) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }
    @Override
    public boolean update(Client objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    @Override
    public Client selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }
    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public Client convertToObject(ResultSet rs)  throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setSurname(rs.getString("surname"));
        client.setPhone(rs.getString("phone"));
        client.setAddress(rs.getString("address"));
        int userId = rs.getInt("userId");
        User user =this.userRepositoryDb.selectById(userId);
        client.setUser(user);
        return client;
    }
    @Override
    public int delete(String sql) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    @Override
    public Client deleteById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
