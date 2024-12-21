package com.example.core.Factory2;

import com.example.core.config.service.Service;

public interface FactoryServiceInterface<T> {
    Service<T> createService();
}
