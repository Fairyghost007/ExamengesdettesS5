package com.example.core.factory1.service;

import com.example.core.factory1.impl.FactoryService;

public class FactoryClient {
    
    public static void main(String[] args) {
        FactoryService<String> stringFactory = new FactoryService<>(String.class);
        stringFactory.create();

        FactoryService<Integer> integerFactory = new FactoryService<>(Integer.class);
        integerFactory.create();
    }
}

