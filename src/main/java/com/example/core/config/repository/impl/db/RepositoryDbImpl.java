package com.example.core.config.repository.impl.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.core.config.database.impl.DataSourceImpl;
import com.example.core.config.repository.Repository;

public abstract class RepositoryDbImpl<T> extends DataSourceImpl implements Repository<T>{

    protected String tableName;

    public abstract T convertToObject(ResultSet rs) throws SQLException;
    
}
