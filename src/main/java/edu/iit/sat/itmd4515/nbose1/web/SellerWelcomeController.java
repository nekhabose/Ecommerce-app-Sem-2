/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;
import edu.iit.sat.itmd4515.nbose1.domain.Seller;
import edu.iit.sat.itmd4515.nbose1.service.SellerService;
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
    public void postConstruct() {
    this.seller = sellerService.findByUsername(loginController.getAuthenticatedUsername());
    if (this.seller == null) {
        LOG.warning("No seller found for username: " + loginController.getAuthenticatedUsername());
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "No seller found for the logged-in user. Please contact support.", null));
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
