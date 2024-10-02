/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1;

import edu.iit.sat.itmd4515.sample.Pet;
import edu.iit.sat.itmd4515.sample.PetType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nekha
 */
public class AbstractJPATest {
    
    private static EntityManagerFactory emf;
    protected EntityManager em;
    protected  EntityTransaction tx;

    @BeforeAll
    public static void beforeAll() {
      
      emf = Persistence.createEntityManagerFactory("itmd4515testPU");
    }

    @BeforeEach
    public void beforeEach() {
       em = emf.createEntityManager();
       tx = em.getTransaction();
       Pet pet = new Pet("TEST DATA", LocalDate.of(2020,12,12), PetType.CAT);
       tx.begin();
       em.persist(pet);
       tx.commit();

    }

    
    
    @AfterEach
    public void afterEach(){
        
        Pet pet = em.createQuery("select p from Pet p where p.name = 'TEST DATA'",Pet.class).getSingleResult();
        System.out.println("beforeEach with pet\t" + pet.toString());
        tx.begin();
        em.remove(pet);
        tx.commit();
        em.close();
    }

    @AfterAll
    public static void afterAll() {
        emf.close();
    }
    
}
