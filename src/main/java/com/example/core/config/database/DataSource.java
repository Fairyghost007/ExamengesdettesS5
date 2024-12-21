package com.example.core.config.database;

import java.sql.*;
import java.util.List;

public interface DataSource {
    void connect() throws SQLException;
    void close() throws SQLException;
    ResultSet executeQuery()throws SQLException;
    void init (String sql) throws SQLException;
    int executeUpdate() throws SQLException;
    // String generateSql(String tableName);
    // void setFields();
}
