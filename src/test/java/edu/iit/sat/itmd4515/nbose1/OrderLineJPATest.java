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
import edu.iit.sat.itmd4515.nbose1.domain.OrderLine;
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
import java.util.Date;
import java.util.List;

public class OrderLineJPATest {

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

    // Create a Customer
    Customer customer = new Customer("Pranav Saji", "psaji@hawk.edu");

    // Create an Order and associate it with the Customer
    Order order = new Order(new Date());
    order.setCustomer(customer);

    // Create an OrderLine and associate it with the Order
    Product product = new Product("Tablet", new BigDecimal("301.99"), new Date(), 50);
    OrderLine orderLine = new OrderLine(product, 2);
    order.addOrderLine(orderLine);

    // Start transaction and persist all entities
    tx.begin();
    em.persist(customer); // Persist Customer first because of the foreign key constraint
    em.persist(product);  // Persist Product
    em.persist(order);    // Persist Order, which also cascades to OrderLine
    tx.commit();
}




    @Test
public void createTest() {
    // Create a new Product
    Product product = new Product("Tablet", new BigDecimal("999.99"), new Date(), 50);

    // Create a new Customer
    Customer customer = new Customer("Pranav Saji", "psaji@hawk.edu");

    // Create a new Order and associate it with the Customer
    Order order = new Order(new Date());
    order.setCustomer(customer);  // Associate the order with the customer

    // Create an OrderLine and associate it with the Order and Product
    OrderLine orderLine = new OrderLine(product, 2);
    order.addOrderLine(orderLine);  // Add OrderLine to Order

    // Begin the transaction
    tx.begin();

    // Persist Customer, Product, and Order
    em.persist(customer);  // Persist the customer first
    em.persist(product);   // Persist the product
    em.persist(order);     // Persist the order (will cascade to OrderLine)

 
    tx.commit();

    // Read back the persisted Order
    Order readBackOrder = em.find(Order.class, order.getId());

    // Assert that the order was persisted and has one order line
    assertNotNull(readBackOrder);
    assertEquals(1, readBackOrder.getOrderLines().size());  // Ensure the order has one order line
    assertEquals("Tablet", readBackOrder.getOrderLines().get(0).getProduct().getName());  // Ensure product details
}


    @Test
public void readTest() {
    List<Order> orders = em.createQuery("select o from Order o", Order.class).getResultList();
    assertFalse(orders.isEmpty());  // Ensure at least one order exists
    Order order = orders.get(0);    // Retrieve the first order
    assertNotNull(order);
    assertEquals(1, order.getOrderLines().size());  // Ensure order has one order line
}


   @Test
public void updateTest() {
    List<Order> orders = em.createQuery("select o from Order o", Order.class).getResultList();
    assertFalse(orders.isEmpty());  // Ensure at least one order exists

    Order order = orders.get(0); // Retrieve the first order
    assertNotNull(order);

    tx.begin();
    order.getOrderLines().get(0).setQuantity(5);  // Update quantity of the first order line
    tx.commit();

    OrderLine updatedOrderLine = em.find(OrderLine.class, order.getOrderLines().get(0).getId());
    assertEquals(5, updatedOrderLine.getQuantity());  // Ensure quantity was updated
}

   @Test
public void deleteTest() {
    List<Order> orders = em.createQuery("select o from Order o", Order.class).getResultList();
    assertFalse(orders.isEmpty());  // Ensure at least one order exists

    Order order = orders.get(0);
    assertNotNull(order);

    tx.begin();
    em.remove(order);  // This will cascade to remove associated OrderLines
    tx.commit();

    Order deletedOrder = em.find(Order.class, order.getId());
    assertNull(deletedOrder);  // Ensure the order was deleted
}


    @AfterEach
public void afterEach() {
    List<Order> orders = em.createQuery("select o from Order o", Order.class).getResultList();
    if (!orders.isEmpty()) {
        tx.begin();
        for (Order order : orders) {
            em.remove(order);  // Remove all orders
        }
        tx.commit();
    }
    em.close();
}


    @AfterAll
    public static void afterAll() {
        emf.close();
    }
}
