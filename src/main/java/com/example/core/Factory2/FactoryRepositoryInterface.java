package com.example.core.Factory2;

import com.example.core.config.repository.Repository;

public interface FactoryRepositoryInterface<T> {
    Repository<T> createRepository();
}
