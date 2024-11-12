/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Nekha
 * @param <T>
 */
public abstract class AbstractService<T> {

    /**
     *
     */
    @PersistenceContext(unitName = "itmd4515testPU")
    protected EntityManager em;

    private final Class<T> entityClass;

    /**
     *
     * @param entityClass
     */
    protected AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     *
     * @param entity
     */
    public void create(T entity) {
        em.persist(entity);
    }

    /**
     *
     * @param id
     * @return
     */
    public T read(Long id) {
        return em.find(entityClass, id);
    }

    /**
     *
     * @param entity
     */
    public void update(T entity) {
        em.merge(entity);
    }

    /**
     *
     * @param entity
     */
    public void delete(T entity) {
        em.remove(em.merge(entity));
    }

    /**
     *
     * @param queryName
     * @return
     */
    public List<T> readAll(String queryName) {
        return em.createNamedQuery(queryName, entityClass).getResultList();
    }
}
