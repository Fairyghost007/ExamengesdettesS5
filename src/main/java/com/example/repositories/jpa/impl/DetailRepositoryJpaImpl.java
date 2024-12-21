package com.example.repositories.jpa.impl;
import java.util.List;

import com.example.entities.Detail;

public interface DetailRepositoryJpaImpl {
    
    List<Detail> searchDetailsOfDetteById(Integer detteId);
    List<Detail> findAllDetailsByDetteId(Integer detteId);
}
