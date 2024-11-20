/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;

import edu.iit.sat.itmd4515.nbose1.domain.Product;
import edu.iit.sat.itmd4515.nbose1.domain.Seller;
import edu.iit.sat.itmd4515.nbose1.service.SellerService;
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
@Named("SellerWelcomeController")
@RequestScoped
public class SellerWelcomeController {
    
    private static final Logger LOG = Logger.getLogger(SellerWelcomeController.class.getName());

    
    private Seller seller;

    @Inject LoginController loginController;
    @EJB SellerService sellerService;
    
    public SellerWelcomeController() {
    }

    @PostConstruct
    private void postConstruct() {

        String authenticatedUsername = loginController.getAuthenticatedUsername();
        LOG.info("Authenticated Username: " + authenticatedUsername);

        // Fetch the seller by username
        seller = sellerService.findByUsername(authenticatedUsername);
        
        // Debugging the seller and their products
        if (seller != null) {
            LOG.info("Seller found: " + seller.toString());
            if (seller.getProducts() == null || seller.getProducts().isEmpty()) {
                LOG.warning("No products associated with this seller.");
            } else {
                for (Product product : seller.getProducts()) {
                    LOG.info("Product: " + product.toString());
                }
            }
        } else {
            LOG.warning("No seller found for username: " + authenticatedUsername);
        }

    }
    
    
    

   
    public void refreshSellerModel(){
        seller = sellerService.findByUsername(loginController.getAuthenticatedUsername());
    }
    
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
