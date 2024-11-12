/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.nbose1.security.Group;
import edu.iit.sat.itmd4515.nbose1.security.GroupService;
import edu.iit.sat.itmd4515.nbose1.security.User;
import edu.iit.sat.itmd4515.nbose1.security.UserService;
import edu.iit.sat.itmd4515.nbose1.domain.Customer;
import edu.iit.sat.itmd4515.nbose1.domain.Order;
import edu.iit.sat.itmd4515.nbose1.domain.OrderLine;
import edu.iit.sat.itmd4515.nbose1.domain.Product;
import edu.iit.sat.itmd4515.nbose1.domain.Seller;
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
    
    @EJB
    private SellerService sellerService;

    /**
     *
     */
    
    //security realm services
    @EJB UserService userSvc;
    @EJB GroupService groupSvc;
    
    @PostConstruct
    public void init() {
        LOG.info("Initializing the database with sample data.");
        
         // Security realm initialization
        Group customerGroup = new Group("CUSTOMER_GROUP", "Group of e-commerce customers");
        Group sellerGroup = new Group("SELLER_GROUP", "Group of product sellers");
        Group adminGroup = new Group("ADMIN_GROUP", "Group of platform admins");
        
        groupSvc.create(customerGroup);
        groupSvc.create(sellerGroup);
        groupSvc.create(adminGroup);
        
        User customer1 = new User("nekha", "nekha");
        customer1.addGroup(customerGroup);
        customer1.addGroup(sellerGroup);
        customer1.addGroup(adminGroup);
        
        User seller1 = new User("pranav", "pranav");
        seller1.addGroup(sellerGroup);
        
        User seller2 = new User("noyal", "noyal");
        seller2.addGroup(sellerGroup);
        
        
        
        User admin = new User("admin", "admin");
        admin.addGroup(adminGroup); 
        
        userSvc.create(customer1);
        userSvc.create(seller1);
        userSvc.create(seller2);
        userSvc.create(admin);

        // Sample data initialization for entities
        
        Seller s1 = new Seller("ArtShop", "art@example.com");
        Seller s2 = new Seller("Macrome", "mc@example.com");

         sellerService.create(s1);
         sellerService.create(s2);
        // Creating Products
        
        Product p1 = new Product("Table", new BigDecimal("6500.00"), new Date(), 1000);
        p1.setSeller(s1);
        Product p2 = new Product("Desktop", new BigDecimal("1800.00"), new Date(), 9500);
        p2.setSeller(s2);

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