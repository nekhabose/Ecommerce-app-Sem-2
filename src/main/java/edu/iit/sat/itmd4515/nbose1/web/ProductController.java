/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;

import edu.iit.sat.itmd4515.nbose1.service.ProductService;
import edu.iit.sat.itmd4515.nbose1.domain.Product;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.logging.Logger;

/**
 *
 * @author Nekha
 */
@Named
@RequestScoped
public class ProductController {

    @EJB
    private ProductService productService;

    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    private Product product;

    /**
     *
     */
    public ProductController() {
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
        LOG.info("Inside ProductController.saveProduct() before calling service: " + product.toString());
        productService.create(product);
        LOG.info("Inside ProductController.saveProduct() after calling service: " + product.toString());
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
