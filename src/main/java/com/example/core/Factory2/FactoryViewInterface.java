package com.example.core.Factory2;

import com.example.core.config.view.View;

public interface FactoryViewInterface<T> {
    View<T> createView();
}
