/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;
import edu.iit.sat.itmd4515.nbose1.domain.Order;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.util.logging.Logger;


/**
 *
 * @author Nekha
 */
@Named
@RequestScoped
public class ConfirmationController {

    private static final Logger LOG = Logger.getLogger(ConfirmationController.class.getName());

    private Order confirmedCart;
    private double totalAmount;

    @PostConstruct
    public void postConstruct() {
        LOG.info("Initializing ConfirmationController...");
        FacesContext context = FacesContext.getCurrentInstance();

        // Retrieve the confirmed cart from the session
        confirmedCart = (Order) context.getExternalContext().getSessionMap().get("confirmedCart");

        if (confirmedCart != null) {
            calculateTotalAmount();
            // Clear the confirmedCart from the session after retrieving it
            context.getExternalContext().getSessionMap().remove("confirmedCart");
            LOG.info("Cleared confirmedCart from session.");
        } else {
            LOG.warning("No confirmedCart found in session.");
        }
    }

    public Order getConfirmedCart() {
        return confirmedCart;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    private void calculateTotalAmount() {
        if (confirmedCart != null && confirmedCart.getOrderLines() != null) {
            totalAmount = confirmedCart.getOrderLines().stream()
                .mapToDouble(line -> line.getProduct().getPrice().doubleValue() * line.getQuantity())
                .sum();
        } else {
            totalAmount = 0.0;
        }
    }
}
