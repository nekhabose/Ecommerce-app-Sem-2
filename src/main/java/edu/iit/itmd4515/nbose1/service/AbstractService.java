/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.itmd4515.nbose1.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Nekha
 */
abstract class AbstractService<T> {

    @PersistenceContext(unitName = "itmd4515testPU")
    protected EntityManager em;

    private final Class<T> entityClass;

    protected AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        em.persist(entity);
    }

    public T read(Long id) {
        return em.find(entityClass, id);
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public void delete(T entity) {
        em.remove(em.merge(entity));
    }

    public List<T> readAll(String queryName) {
        return em.createNamedQuery(queryName, entityClass).getResultList();
    }
}
