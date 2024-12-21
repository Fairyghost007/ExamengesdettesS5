package com.example.repositories.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;

import com.example.entities.Article;
import com.example.repositories.jpa.impl.ArticleRepositoryJpaImpl;

public class ArticleRepositoryJpa extends RepositoryJpaImpl<Article> implements ArticleRepositoryJpaImpl {

    public ArticleRepositoryJpa() {
        super(Article.class); 
    }

    @Override
    public List<Article> findAllArticleDisponible() {
        String jpql = "SELECT a FROM Article a WHERE a.qteStock > 0";
        TypedQuery<Article> query = em.createQuery(jpql, Article.class);
        return query.getResultList();
    }

    public Article findByLibelle(String libelle) {
        try {
            // Assuming you have a query that looks for the article by libelle
            return em.createQuery("SELECT a FROM Article a WHERE a.libelle = :libelle", Article.class)
                    .setParameter("libelle", libelle)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no entity is found
        }
    
}

    public List<Article> findAllArticleNonDisponible() {
        String jpql = "SELECT a FROM Article a WHERE a.qteStock = 0";
        TypedQuery<Article> query = em.createQuery(jpql, Article.class);
        return query.getResultList();
    }

    
}
