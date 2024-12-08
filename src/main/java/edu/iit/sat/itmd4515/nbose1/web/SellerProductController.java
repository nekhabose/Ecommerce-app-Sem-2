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
//@ViewScoped
@RequestScoped
public class SellerProductController {

     @EJB ProductService productSvc;
     @EJB SellerService sellerSvc;
     @Inject SellerWelcomeController swc;
    

    private static final Logger LOG = Logger.getLogger(SellerProductController.class.getName());

    private Product product;

    /**
     *
     */
    public SellerProductController() {
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
   
    public String saveProduct() {
    try {
        LOG.info("Saving product: " + product.toString());
        Seller authenticatedSeller = swc.getSeller();
        if (authenticatedSeller != null) {
            sellerSvc.createProductForAuthenticatedSeller(product, authenticatedSeller);
            LOG.info("Product successfully saved for seller: " + authenticatedSeller.getStoreName());
            LOG.info("Saving gvdproduct: " + product.toString());
            swc.refreshSellerModel();
            return "/seller/welcome.xhtml?faces-redirect=true"; // Correct navigation
        } else {
            LOG.warning("No authenticated seller found.");
            swc.refreshSellerModel();
            return "/error.xhtml";
        }
    } catch (Exception e) {
        LOG.severe("Error saving product: " + e.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "An error occurred while saving the product.", "Please try again later."));
        swc.refreshSellerModel();
        return "/error.xhtml";
    }
}

    // Getters and Setters

    /**
     *
     * @return
     */
   
   
   public String displayProductDetailsPage(Product p){
        // Step #1 - Set the model
        this.product = p;
        LOG.info("inside displayProductDetailsPage with Product " + product.toString());
        // Step #2 - Navigate to the appropriate view
        return "/seller/productDetails.xhtml";
    }

    /**
     *
     * @param p
     * @return
     */
   
    public String displayEditProductPage(Product p) {

        this.product = p;
            
        LOG.info("Inside displayEditProductPage with Product: " + product.toString());
//        return "/seller/editProduct.xhtml?faces-redirect=true";
          return "/seller/editProduct.xhtml";
    }
  
   
    /**
     *
     * @param 
     * @return
     */
    public String displayDeleteProductPage(Product p){
        // Step #1 - Set the model
        this.product = p;
        LOG.info("inside nlnlnlnldisplayDeleteProductPage with Product " + product.toString());
        // Step #2 - Navigate to the appropriate view
        return "/seller/deleteProduct.xhtml";
    }

    /**
     *
     * @return
     */
    public String displayAddProductPage(){
       
        LOG.info("inside displayAddProductPage");
        // Step #2 - Navigate to the appropriate view
        return "/seller/createProduct.xhtml?faces-redirect=true";
    }
    

 public String editProduct() {
    try {
        
        if (product == null) {
            
            LOG.severe("Product or Product ID is null. Cannot proceed with editing.");
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid product data.", "Please try again."));
            return null;
        }

        LOG.info("Editing product: " + product);
        productSvc.editProductForAuthenticatedSeller(product, swc.getSeller());
        swc.refreshSellerModel();
        return "/seller/welcome.xhtml?faces-redirect=true";
    } catch (Exception e) {
        LOG.severe("Error editing product: " + e.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating product.", "Please try again later."));
        return null;
    }
}
    





    /**
     *
     * @return
     */
    
    public String deleteProduct(){
        // Step #3 - invoke the application to do whatever is needed.  In this
        // case, save to the database through the service layer

        LOG.info("Inside ProductController.deletePet() before call to service: " + product.toString());
        productSvc.deleteProductForAuthenticatedSeller(product, swc.getSeller());
        
        swc.refreshSellerModel();
        return "/seller/welcome.xhtml";
    }


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
