package com.example.core.factory1.impl;

import com.example.core.config.factory.FactoryServiceInterface;

public class FactoryService<T> implements FactoryServiceInterface<T> {
    
    private final Class<T> type;

    public FactoryService(Class<T> type) {
        this.type = type;
    }

    @Override
    public T create() {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create an instance of " + type.getName(), e);
        }
    }
}
