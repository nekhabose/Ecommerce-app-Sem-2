/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1;

/**
 *
 * @author Nekha
 */
import edu.iit.sat.itmd4515.nbose1.domain.Customer;
import edu.iit.sat.itmd4515.nbose1.domain.Order;
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

import java.util.Date;
import java.util.List;








public class CustomerOrderJPATest {

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

        // Create and persist a Customer
        Customer customer = new Customer("Pranav saji", "psaji@hawk.edu");
        Order order = new Order(new Date());
        customer.addOrder(order);

        tx.begin();
        em.persist(customer);  // Persisting Customer will cascade the persistence of the Order
        tx.commit();
    }

    @Test
    public void createTest() {
        Customer customer = new Customer("Nekah", "nekha@hawk.edu");
        Order order = new Order(new Date());
        customer.addOrder(order);

        tx.begin();
        em.persist(customer);
        tx.commit();

        Customer readBackFromDatabase = em.find(Customer.class, customer.getId());
        assertNotNull(readBackFromDatabase);  // Check that the customer was persisted
        assertEquals(1, readBackFromDatabase.getOrders().size());  // Ensure the order was also persisted
    }

    @Test
    public void readTest() {
        Customer customer = em.createQuery("select c from Customer c where c.name = 'Pranav saji'", Customer.class).getSingleResult();
        assertNotNull(customer);  // Ensure customer was found
        assertEquals("psaji@hawk.edu", customer.getEmail());  
        assertEquals(1, customer.getOrders().size());  
    }

    @Test
    public void updateTest() {
       List<Customer> customers = em.createQuery("select c from Customer c where c.name = 'Pranav saji'", Customer.class).getResultList();
    if (!customers.isEmpty()) {
    Customer customer = customers.get(0);
    // Update the customer
    tx.begin();
    customer.setEmail("updatedemail@example.com");
    tx.commit();

    Customer updatedCustomer = em.find(Customer.class, customer.getId());
    assertEquals("updatedemail@example.com", updatedCustomer.getEmail());
} else {
    fail("Customer not found for update");
}

    }

    @Test
    public void deleteTest() {
        Customer customer = em.createQuery("select c from Customer c where c.name = 'Pranav saji'", Customer.class).getSingleResult();
        assertNotNull(customer);  // Ensure customer exists

        tx.begin();
        em.remove(customer);
        tx.commit();

        Customer deletedCustomer = em.find(Customer.class, customer.getId());
        assertNull(deletedCustomer);  // Ensure the customer was deleted
    }

    @AfterEach
public void afterEach() {
    List<Customer> customers = em.createQuery("select c from Customer c where c.name = 'Pranav saji'", Customer.class).getResultList();
    
    if (!customers.isEmpty()) {
        Customer customer = customers.get(0);  // Get the first customer
        tx.begin();
        em.remove(customer);  // Clean up customer after tests
        tx.commit();
    }
    em.close();
}


    @AfterAll
    public static void afterAll() {
        emf.close();
    }
}