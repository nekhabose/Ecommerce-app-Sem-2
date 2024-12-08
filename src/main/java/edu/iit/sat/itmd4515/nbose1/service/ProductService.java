/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.nbose1.domain.Product;
import edu.iit.sat.itmd4515.nbose1.domain.Seller;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
/**
 *
 * @author Nekha
 */
@Stateless
@Named
public class ProductService extends AbstractService<Product> {

    /**
     *
     */
    public ProductService() {
        super(Product.class);
    }
        private static final Logger LOG = Logger.getLogger(ProductService.class.getName());


    /**
     *
     * @return
     */
    public List<Product> readAll() {
        return super.readAll("Product.readAll");
        
        
    }
    
    public List<Product> searchProducts(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        LOG.info("Executing searchProducts with name: " + name + ", minPrice: " + minPrice + ", maxPrice: " + maxPrice);
        StringBuilder jpql = new StringBuilder("SELECT p FROM Product p WHERE 1=1");
        
        if (name != null && !name.trim().isEmpty()) {
            jpql.append(" AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))");
        }
        if (minPrice != null) {
            jpql.append(" AND p.price >= :minPrice");
        }
        if (maxPrice != null) {
            jpql.append(" AND p.price <= :maxPrice");
        }

        TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);
        
        if (name != null && !name.trim().isEmpty()) {
            query.setParameter("name", name);
        }
        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }

        List<Product> results = query.getResultList();
        LOG.info("Found " + results.size() + " products matching criteria.");
        return results;
    }

    
    
    public void editProductForAuthenticatedSeller(Product product, Seller seller) {
//        if (product == null || product.getId() == null) {
//            throw new IllegalArgumentException("Product or Product ID cannot be null for editing.");
//        }
        Product managedRef = em.getReference(Product.class, product.getId());
        managedRef.setName(product.getName());
        managedRef.setPrice(product.getPrice());
        managedRef.setManufactureDate(product.getManufactureDate());
        managedRef.setStock(product.getStock());
        em.merge(managedRef);
    }

    public Product findProductById(Long id) {
    TypedQuery<Product> query = em.createQuery(
        "SELECT p FROM Product p JOIN FETCH p.seller WHERE p.id = :id", Product.class);
    query.setParameter("id", id);
    try {
        return query.getSingleResult();
    } catch (NoResultException e) {
        return null;
    }
}


    public Product findProductByName(String name) {
    try {
        return em.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", name)
                .getSingleResult();
    } catch (Exception e) {
        //LOG.severe("Error finding product with name: " + name + ". " + e.getMessage());
        return null;
    }
}

     
    public void deleteProductForAuthenticatedSeller(Product product, Seller seller) {
    LOG.info("Inside deleteProductForAuthenticatedSeller");
    
    // Fetch managed references
    Seller managedSellerRef = em.getReference(Seller.class, seller.getId());
    Product managedProductRef = em.getReference(Product.class, product.getId());

    // Remove product from seller's collection
    managedSellerRef.getProducts().remove(managedProductRef);

    // Remove the product entity
    em.remove(managedProductRef);
   
    // Merge the changes in the seller (if necessary)
    em.merge(managedSellerRef);
}

}
