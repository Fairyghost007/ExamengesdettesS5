package com.example.core.config.database.impl;

import java.sql.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import com.example.core.config.database.DataSource;

public class DataSourceImpl implements DataSource {
    private final String url = "jdbc:postgresql://localhost:5432/gesDette";
    private final String username = "postgres";
    private final String password = "ghost";
    protected PreparedStatement ps;
    private Connection conn=null;
    @Override
    public void connect() throws SQLException {
        try {
            Class.forName("com.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return ps.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        return ps.executeUpdate();
    }

    @Override
    public void init(String sql) throws SQLException {
        this.connect();
        ps=sql.toUpperCase().trim().startsWith("INSERT")?conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS):conn.prepareStatement(sql);
        
    }
    
}
