package com.example.services;

import com.example.core.config.service.impl.ServiceImpl;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.entities.Article;
import com.example.repositories.jpa.ArticleRepositoryJpa;

import java.util.List;



public class ArticleServiceImpl extends ServiceImpl<Article>{
    private RepositoryJpaImpl<Article> repositoryJpaImpl;
    private ArticleRepositoryJpa articleRepositoryJpa;

    public ArticleServiceImpl(ArticleRepositoryJpa articleRepositoryJpa) {
        this.repositoryJpaImpl= new RepositoryJpaImpl<>(Article.class);
        this.articleRepositoryJpa = articleRepositoryJpa;
    }

    public void create(Article article) {
        repositoryJpaImpl.insert(article);
    }

    public List<Article> findAll() {
        return repositoryJpaImpl.selectAll();
    }

    public List<Article> findAllArticleDisponible() {
        return articleRepositoryJpa.findAllArticleDisponible();
    }

    public List<Article> findAllArticleNonDisponible() {
        return articleRepositoryJpa.findAllArticleNonDisponible();
    }

    public Article getById(Integer id) {
        return repositoryJpaImpl.selectById(id);
    }

    public void update(Article article) {
        repositoryJpaImpl.update(article);
    }

    public Article findByLibelle(String libelle) {
        return articleRepositoryJpa.findByLibelle(libelle);
    }
    
}
