/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1;


import edu.iit.sat.itmd4515.nbose1.domain.Pet;
import edu.iit.sat.itmd4515.nbose1.domain.PetType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author sas
 */
public class PetJPATest {

 private static EntityManagerFactory emf;
 private EntityManager em;
 private EntityTransaction tx;

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

    @Test
    public void createTest()  {
        // one possibility - assert that you can read beforeEach pet, and 
        // if you can, then you know the create was successful
        // or 
        // create a new pet, then assert it was successful
        
       Pet pet = new Pet("dhddfpuppy", LocalDate.of(2020,12,12), PetType.DOG);
       tx.begin();
       em.persist(pet);
       tx.commit();

       Pet readBackFromDatabase = em.find(Pet.class, pet.getId());
       assertNotNull(readBackFromDatabase);
       assertTrue(readBackFromDatabase.getId() > 0);
       assertEquals("dhddfpuppy", readBackFromDatabase.getName());
       
    }
    @Test
    public void readTest() {
        // left as an exercise for you - see above for an example or idea
    }

    @Test
    public void updateTest() {
        
        //first I will read a pet back from the db and assert
        //then I will update something and save it to the database
        //then i will read it back from the database again and assert
        //that the update was saved to the database
       Pet pet = em.createQuery("select p from Pet p where p.name = 'TEST DATA'",Pet.class).getSingleResult();
       assertTrue(pet.getId() >  0);
       
       tx.begin();
        pet.setBirthDate(LocalDate.of(2024, 1, 1));
        tx.commit();
       
      Pet readBackFromDatabase = em.createQuery("select p from Pet p where p.name = 'TEST DATA'",Pet.class).getSingleResult();
      assertEquals(pet.getId(), readBackFromDatabase.getId());
      
      assertEquals(LocalDate.of(2024, 1, 1),readBackFromDatabase.getBirthDate());
    }
    
    @Test
    public void deleteTest() {
        // first, create a new customer to delete
        // then delete it
        // finally, try to read it back from the database by ID
        // and assert that it was not found ( it would be null if not found)
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
