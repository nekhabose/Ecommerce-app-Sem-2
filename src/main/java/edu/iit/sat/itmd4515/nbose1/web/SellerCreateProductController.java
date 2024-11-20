/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;

import edu.iit.sat.itmd4515.nbose1.service.ProductService;
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
@Named
@RequestScoped
public class SellerCreateProductController {

   // @EJB private ProductService productService;
     @EJB SellerService sellerSvc;
     @Inject SellerWelcomeController swc;
    

    private static final Logger LOG = Logger.getLogger(SellerCreateProductController.class.getName());

    private Product product;

    /**
     *
     */
    public SellerCreateProductController() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("Inside ProductController.postConstruct()");
        product = new Product();
    }

    /**
     *
     * @return
     */
   /* public String saveProduct() {
        LOG.info("Inside ProductController.saveProduct() before calling service: " + product.toString());
        // productService.create(product);
        sellerSvc.createProductForAuthenticatedSeller(product, swc.getSeller());
               
        LOG.info("Inside ProductController.saveProduct() after calling service: " + product.toString());
        return "confirmation.xhtml";
    }*/
   public String saveProduct() {
        LOG.info("Inside SellerCreateProductController.saveProduct() before saving: " + product.toString());

        // Retrieve the authenticated seller from SellerWelcomeController
        Seller authenticatedSeller = swc.getSeller();
        if (authenticatedSeller != null) {
            sellerSvc.createProductForAuthenticatedSeller(product, authenticatedSeller);
            LOG.info("Product saved successfully for seller: " + authenticatedSeller.getStoreName());
        } else {
            LOG.warning("No authenticated seller found.");
        }

        LOG.info("Inside SellerCreateProductController.saveProduct() after saving: " + product.toString());
        return "confirmation.xhtml";
    }
    
    

    // Getters and Setters

    /**
     *
     * @return
     */
    public Product getProduct() {
        return product;
    }

    /**
     *
     * @param product
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}
