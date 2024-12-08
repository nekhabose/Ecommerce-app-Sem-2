/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;

import edu.iit.sat.itmd4515.nbose1.domain.Order;
import edu.iit.sat.itmd4515.nbose1.domain.OrderLine;
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

    @Named
    @RequestScoped
    public class CustomerCartController {

        private static final Logger LOG = Logger.getLogger(CustomerCartController.class.getName());

        @EJB
        private OrderService orderSvc;

        @Inject
        private CustomerWelcomeController customerWelcomeController;

        private Order cart;

        @PostConstruct
        public void postConstruct() {
            LOG.info("Initializing CustomerCartController...");
            loadCart();
        }

        private void loadCart() {
            cart = orderSvc.findCartByCustomer(customerWelcomeController.getCust());
            if (cart == null) {
                LOG.info("No existing cart for customer " + customerWelcomeController.getCust().getId() + ". A new cart will be created upon adding a product.");
            } else {
                LOG.info("Loaded cart: " + cart.toString());
            }
        }

        // Getters and Setters
        public Order getCart() {
            return cart;
        }

        public void setCart(Order cart) {
            this.cart = cart;
        }

        // Action Methods
        public String proceedToCheckout() {
            if (cart == null || cart.getOrderLines().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Empty Cart", "Your cart is empty."));
                return null; // Stay on the same page
            }
            // Perform checkout operations here if needed
            return "checkout.xhtml?faces-redirect=true";
        }

        public double getTotalAmount() {
            if (cart == null || cart.getOrderLines() == null) {
                return 0.0;
            }
            return cart.getOrderLines().stream()
                    .mapToDouble(line -> line.getProduct().getPrice().doubleValue() * line.getQuantity())
                    .sum();
        }

        public String updateCartItem(OrderLine orderLine, int newQuantity) {
            LOG.info("Updating cart item: " + orderLine.getId() + " to quantity: " + newQuantity);
            try {
                orderSvc.updateCartItem(orderLine, newQuantity);
                loadCart(); // Reload cart after update
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cart Updated", "Cart item updated successfully."));
            } catch (InsufficientStockException e) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", e.getMessage()));
            }
            return null; // Stay on the same page
        }

        public String removeCartItem(OrderLine orderLine) {
            LOG.info("Removing cart item: " + orderLine.getId());
            orderSvc.removeCartItem(orderLine);
            loadCart(); // Reload cart after removal
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Removed", "Product removed from cart."));
            return null; // Stay on the same page
        }

        public double calculateTotalAmount() {
            if (cart == null || cart.getOrderLines() == null) {
                return 0.0;
            }
            return cart.getOrderLines().stream()
                .mapToDouble(line -> line.getProduct().getPrice().doubleValue() * line.getQuantity())
                .sum();
        }
    }
