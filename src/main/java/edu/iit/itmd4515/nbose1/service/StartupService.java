/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.nbose1.domain.Customer;
import edu.iit.sat.itmd4515.nbose1.domain.Order;
import edu.iit.sat.itmd4515.nbose1.domain.OrderLine;
import edu.iit.sat.itmd4515.nbose1.domain.Product;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Nekha
 */
@Startup
@Singleton
public class StartupService {

    private static final Logger LOG = Logger.getLogger(StartupService.class.getName());

    @EJB
    private CustomerService customerService;

    @EJB
    private OrderService orderService;

    @EJB
    private OrderLineService orderLineService;

    @EJB
    private ProductService productService;

    @PostConstruct
    public void init() {
        LOG.info("Initializing the database with sample data.");

        // Creating Products
        Product p1 = new Product("Table", new BigDecimal("6500.00"), new Date(), 1000);
        Product p2 = new Product("Desktop", new BigDecimal("1800.00"), new Date(), 9500);

        productService.create(p1);
        productService.create(p2);

        // Creating Customers
        Customer c1 = new Customer("Noyal Joseph Binu", "binujoseph@hawk.iit.edu");
        Customer c2 = new Customer("Pranav Saji", "saji@hawk.iit.edu");

        customerService.create(c1);
        customerService.create(c2);

        // Creating Orders and OrderLines
        Order o1 = new Order(new Date());
        Order o2 = new Order(new Date());

        o1.addOrderLine(new OrderLine(p1, 1));
        o1.addOrderLine(new OrderLine(p2, 2));

        o2.addOrderLine(new OrderLine(p2, 1));

        c1.addOrder(o1);
        c2.addOrder(o2);

        orderService.create(o1);
        orderService.create(o2);

        LOG.info("Sample data created.");

        // Output Section: Demonstrating relationships
        LOG.info("-----------------------------------------------------------------------------");
        LOG.info("DEMONSTRATING output section for relationships");
        LOG.info("-----------------------------------------------------------------------------\n");
        
        for (Customer customer : customerService.readAll()) {
            LOG.info("-----------------------------------------------------------------------------");
            LOG.info(customer.toString());

            LOG.info("\tBi-directional relationship from Customer (inverse) to Order >>>>>>>>>");
            for (Order order : customer.getOrders()) {
                LOG.info("\t" + order.toString());
                
                LOG.info("\t\tBi-directional relationship from Order (inverse) to OrderLine >>>>>>>>>");
                for (OrderLine orderLine : order.getOrderLines()) {
                    LOG.info("\t\t" + orderLine.toString());
                    
                    LOG.info("\t\t\tUnidirectional relationship from OrderLine (owner) to Product >>>>>>>>>");
                    LOG.info("\t\t\t" + orderLine.getProduct().toString());
                }
            }
        }
    }
}