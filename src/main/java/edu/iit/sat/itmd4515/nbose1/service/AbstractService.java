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
 */
abstract class AbstractService<T> {

    @PersistenceContext(name = "itmd4515PU")
    protected EntityManager em;

    protected Class<T> entityClass;
    
    protected AbstractService(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    public void create(T e) {
        em.persist(e);
    }

    public T read(Long id) {
        return em.find(entityClass, id);
    }

    public void update(T e) {
        em.merge(e);
    }

    public void delete(T e) {
        em.remove(em.merge(e));
    }
    
    protected List<T> readAll(String namedQueryName){
        return em.createNamedQuery(namedQueryName, entityClass).getResultList();
    }

    
}

