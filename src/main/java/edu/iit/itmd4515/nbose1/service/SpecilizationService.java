/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.sample.Specilization;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Nekha
 */
@Stateless
public class SpecilizationService {

    @PersistenceContext(name = "itmd4515PU")
    private EntityManager em;

    /**
     *
     */
    public SpecilizationService() {
    }

    /**
     *
     * @param s
     */
    public void create(Specilization s) {
        // the EntityTransaction from our test case work is no longer
        // necessary, because EJB methods are transactional by nature, and
        // default transactions provided and manged by JTA
        //tx.begin();
        em.persist(s);
        //tx.commit();
    }

    /**
     *
     * @param id
     * @return
     */
    public Specilization read(Long id) {
        return em.find(Specilization.class, id);
    }

    /**
     *
     * @param s
     */
    public void update(Specilization s) {
        em.merge(s);
    }

    /**
     *
     * @param s
     */
    public void delete(Specilization s) {
        em.remove(em.merge(s));
    }
    
    /**
     *
     * @return
     */
    public List<Specilization> readAll(){
        //return em.createQuery("select s from Specialization s", Specialization.class).getResultList();
        return em.createNamedQuery("Specialization.readAll", Specilization.class).getResultList();
    }

}
