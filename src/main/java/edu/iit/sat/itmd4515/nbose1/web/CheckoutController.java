/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;
import edu.iit.sat.itmd4515.nbose1.domain.Order;
import edu.iit.sat.itmd4515.nbose1.service.OrderService;
import edu.iit.sat.itmd4515.nbose1.service.InsufficientStockException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.logging.Logger;

/**
 *
 * @author Nekha
 */




@Named
@RequestScoped
public class CheckoutController {

    private static final Logger LOG = Logger.getLogger(CheckoutController.class.getName());

    @EJB
    private OrderService orderSvc;

    @Inject
    private CustomerCartController cartController;

    private Order cart;

    @PostConstruct
    public void postConstruct() {
        LOG.info("Initializing CheckoutController...");
        cart = cartController.getCart();
    }

    // Getters and Setters
    public Order getCart() {
        return cart;
    }
    public double calculateTotalAmount() {
    if (cart == null || cart.getOrderLines() == null) {
        return 0.0;
    }
    return cart.getOrderLines().stream()
        .mapToDouble(line -> line.getProduct().getPrice().doubleValue() * line.getQuantity())
        .sum();
}


    // Action Method
    public String checkout() {
        if (cart == null || cart.getOrderLines().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Empty Cart", "Your cart is empty."));
            return null; // Stay on the same page
        }

        try {
            orderSvc.checkout(cart);
            LOG.info("Checkout successful for Order ID: " + cart.getId());
            // Store the cart in the session for confirmation page
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("confirmedCart", cart);

            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Checkout Successful", "Your order has been placed."));
            return "confirmation.xhtml?faces-redirect=true";
        } catch (InsufficientStockException e) {
            LOG.warning("Checkout failed: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Checkout Failed", e.getMessage()));
            return null;
        } catch (Exception e) {
            LOG.severe("Unexpected error during checkout: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Checkout Failed", "An unexpected error occurred."));
            return null;
        }
    }
}
