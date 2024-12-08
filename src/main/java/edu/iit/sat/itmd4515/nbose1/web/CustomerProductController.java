/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;
import edu.iit.sat.itmd4515.nbose1.domain.Product;
import edu.iit.sat.itmd4515.nbose1.service.InsufficientStockException;
import edu.iit.sat.itmd4515.nbose1.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Nekha
 */




@Named
@RequestScoped
public class CustomerProductController {

    private static final Logger LOG = Logger.getLogger(CustomerProductController.class.getName());

    @EJB
    private ProductService productService;

    @Inject
    private CustomerWelcomeController customerWelcomeController;

    private List<Product> products;
    private String searchName;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    @PostConstruct
    public void postConstruct() {
        LOG.info("Initializing CustomerProductController...");
        loadAllProducts();
    }

    private void loadAllProducts() {
        products = productService.readAll();
        LOG.info("Loaded " + products.size() + " products for browsing.");
    }

    // Getters and Setters
    public List<Product> getProducts() {
        return products;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    // Action Methods
    public String searchProducts() {
        LOG.info("Searching products with name: " + searchName + ", minPrice: " + minPrice + ", maxPrice: " + maxPrice);
        products = productService.searchProducts(searchName, minPrice, maxPrice);
        if (products.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "No Products Found", "No products match your search criteria."));
        }
        return null; // Stay on the same page
    }

    public String addToCart(Product product) {
        LOG.info("Adding product to cart: " + product.getName());
        try {
            customerWelcomeController.addToCart(product, 1); // Default quantity is 1
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Added to Cart", "Product added to your cart."));
        } catch (InsufficientStockException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insufficient Stock", e.getMessage()));
        } catch (Exception e) {
            LOG.severe("Error adding to cart: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Could not add product to cart."));
        }
        return null; // Stay on the same page
    }
}

