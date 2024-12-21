package com.example.core.config.factory;

import com.example.core.config.repository.Repository;

public interface FactoryServiceInterface<T> {
    // Repository<T> create();  
    T create();  
}
