package com.example.repositories.db;

import com.example.core.config.repository.impl.db.RepositoryDbImpl;
import com.example.entities.Client;
import com.example.entities.User;
import com.example.enums.Role;
import com.example.repositories.db.impl.UserRepositoryDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryDbImpl extends RepositoryDbImpl<User> implements UserRepositoryDb {
    public UserRepositoryDbImpl() {
        
        tableName = "users";
    }

    @Override
    public void insert(User user) {
        try{
            String sql =String.format("INSERT INTO %s (email, login, password, \"roleId\", statut) VALUES (?, ?, ?,?, 1)", this.tableName);
            this.init(sql);
            this.ps.setString(1, user.getEmail());
            this.ps.setString(2, user.getLogin());
            this.ps.setString(3, user.getPassword());
            try {
                // this.ps.setInt(4, user.getRole().getId());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if(rs.next()){
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                this.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User selectByLogin(String login) {
        User user = null;
        try{
            String sql=String.format("SELECT * FROM %s WHERE login = ?",this.tableName);
            this.init(sql);
            this.ps.setString(1, login);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
                user = this.convertToObject(rs);
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
        return user;
    }
    @Override
    public User selectById(int id) {
        User user = null;
        try{
            String sql=String.format("SELECT * FROM %s WHERE id = ?",this.tableName);
            this.init(sql);
            this.ps.setInt(1, id);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
                user = this.convertToObject(rs);
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
        return user;
    }
    @Override
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        try{
            String sql =String.format("SELECT * FROM %s",this.tableName);
            this.init(sql);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                users.add( this.convertToObject(rs));
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
        return users;
    }



    @Override
    public int insert(String sql) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }
    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public User convertToObject(ResultSet rs)  throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setPassword("**********");
        // user.setRole(Role.getValue2("roleId")));
        user.setRole(Role.getValue(rs.getString("roleId")));
        user.setStatut(rs.getBoolean("statut") );
        return user;
    }

    @Override
    public boolean update(User objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(String sql) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public User deleteById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }



    
}
