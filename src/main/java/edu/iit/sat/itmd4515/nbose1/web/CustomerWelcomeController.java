/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;

import edu.iit.sat.itmd4515.nbose1.domain.Customer;
import edu.iit.sat.itmd4515.nbose1.domain.Product;
import edu.iit.sat.itmd4515.nbose1.service.CustomerService;
import edu.iit.sat.itmd4515.nbose1.service.InsufficientStockException;
import edu.iit.sat.itmd4515.nbose1.service.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.logging.Logger;

/**
 *
 * @author Nekha
 */
@Named("CustomerWelcomeController") 
@RequestScoped
public class CustomerWelcomeController {
    
private static final Logger LOG = Logger.getLogger(CustomerWelcomeController.class.getName());

    private Customer cust ;

    @Inject 
     private LoginController loginController;
    @EJB 
     private CustomerService custSvc;
    @EJB
    private OrderService orderService;
    
    public CustomerWelcomeController() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("Inside CustomerWelcomeController.postConstruct with " + loginController.getAuthenticatedUsername() );
        cust = custSvc.findByUsername(loginController.getAuthenticatedUsername());
        if (cust != null) {
            LOG.info("Customer fetched: " + cust.toString());
        } else {
            LOG.warning("No customer found for username: " + loginController.getAuthenticatedUsername());
        }
    }

    

    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }
    
    public void addToCart(Product product, int quantity) throws InsufficientStockException {
        LOG.info("Customer " + cust.getId() + " adding product " + product.getId() + " to cart with quantity " + quantity);
        orderService.addToCart(cust, product, quantity);
    }

}
