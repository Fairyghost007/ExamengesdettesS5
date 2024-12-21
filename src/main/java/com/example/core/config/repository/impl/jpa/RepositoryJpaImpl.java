
package com.example.core.config.repository.impl.jpa;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.example.core.config.repository.Repository;
import org.yaml.snakeyaml.Yaml;

public class RepositoryJpaImpl<T> implements Repository<T> {
    protected EntityManager em;
    private Class<T> entityClass;
    private EntityManagerFactory emf;
    
    public RepositoryJpaImpl(Class<T> type) {
        this.entityClass = type; 
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.yaml")) {
            Map<String, Object> config = yaml.load(inputStream);
            String persistenceUnit = (String) ((Map<String, Object>) config.get("config")).get("persistance");

            emf = Persistence.createEntityManagerFactory(persistenceUnit);

            // if (em == null) {
                em = emf.createEntityManager();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(T object) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(object);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public int insert(String sql) {
        EntityTransaction transaction = em.getTransaction();
        int rowsAffected = 0;
        try {
            transaction.begin();
            rowsAffected = em.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public boolean update(T object) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public T selectById(int id) {
        return em.find(entityClass, id);
    }

    @Override
    public int count() {
        String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
        return ((Long) em.createQuery(jpql).getSingleResult()).intValue();
    }

    @Override
    public List<T> selectAll() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return em.createQuery(jpql, entityClass).getResultList();
    }

    @Override
    public int delete(String sql) {
        EntityTransaction transaction = em.getTransaction();
        int rowsAffected = 0;
        try {
            transaction.begin();
            rowsAffected = em.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public T deleteById(int id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                transaction.commit();
                return entity; // Return the deleted entity
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null; // Return null if no entity was deleted
    }


    
}

