/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1;
import edu.iit.sat.itmd4515.nbose1.domain.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;


/**
 *
 * @author Nekha
 */
public class ProductJPATest {

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

        
        Product product = new Product("TEST PRODUCT", new BigDecimal("100.00"), new Date(System.currentTimeMillis()), 10);
        tx.begin();
        em.persist(product);
        tx.commit();
    }

    @Test
    public void createTest() {
        
        Product product = new Product("Watch", new BigDecimal("899.99"), new Date(System.currentTimeMillis()), 25);

        tx.begin();
        em.persist(product);
        tx.commit();

        Product readBackFromDatabase = em.find(Product.class, product.getId());
        assertNotNull(readBackFromDatabase);  // Check that the product was persisted
        assertTrue(readBackFromDatabase.getId() > 0);  // Ensure ID was auto-generated
        assertEquals("Watch", readBackFromDatabase.getName());  // Validate name
    }

    @Test
    public void readTest() {
        
        Product product = em.createQuery("select p from Product p where p.name = 'TEST PRODUCT'", Product.class).getSingleResult();
        assertNotNull(product);  // Ensure product was found
        assertTrue(product.getId() > 0);  // Check ID is valid
        assertEquals("TEST PRODUCT", product.getName());  // Validate product name
        assertEquals(new BigDecimal("100.00"), product.getPrice());  // Validate product price
    }

    @Test
    public void updateTest() {
       
        Product product = em.createQuery("select p from Product p where p.name = 'TEST PRODUCT'", Product.class).getSingleResult();
        assertTrue(product.getId() > 0);  // Check that the product exists

        tx.begin();
        product.setPrice(new BigDecimal("150.00"));  // Update product price
        tx.commit();

        Product updatedProduct = em.createQuery("select p from Product p where p.name = 'TEST PRODUCT'", Product.class).getSingleResult();
        assertEquals(new BigDecimal("150.00"), updatedProduct.getPrice());  // Ensure the price was updated
    }
 
    @Test
    public void deleteTest() {
        // Test case for deleting a product
        Product product = new Product("Delete Me", new BigDecimal("50.00"), new Date(System.currentTimeMillis()), 5);

        tx.begin();
        em.persist(product);
        tx.commit();

        // Now delete the product
        Product productToDelete = em.find(Product.class, product.getId());
        assertNotNull(productToDelete);  // Ensure the product exists before deletion

        tx.begin();
        em.remove(productToDelete);
        tx.commit();

        Product deletedProduct = em.find(Product.class, product.getId());
        assertNull(deletedProduct);  // After deletion, product should not exist
    }

    @AfterEach
    public void afterEach() {
        // Clean up test data after each test
        Product product = em.createQuery("select p from Product p where p.name = 'TEST PRODUCT'", Product.class).getSingleResult();
        if (product != null) {
            tx.begin();
            em.remove(product);
            tx.commit();
        }
        em.close();  
    }

    @AfterAll
    public static void afterAll() {
        
        emf.close();
    }
}