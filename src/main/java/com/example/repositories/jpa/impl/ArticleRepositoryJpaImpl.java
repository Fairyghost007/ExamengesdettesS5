package com.example.repositories.jpa.impl;

import java.util.List;

import com.example.entities.Article;

public interface ArticleRepositoryJpaImpl {
    List<Article> findAllArticleDisponible();

    Article findByLibelle(String libelle);
}
