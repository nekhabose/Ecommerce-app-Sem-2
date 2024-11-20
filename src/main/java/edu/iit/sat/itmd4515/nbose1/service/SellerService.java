/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.nbose1.domain.Product;
import edu.iit.sat.itmd4515.nbose1.domain.Seller;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author Nekha
 */
@Stateless
public class SellerService extends AbstractService<Seller> {

    public SellerService() {
        super(Seller.class);
    }

    public List<Seller> readAll() {
        return super.readAll("Seller.readAll");
    }
    
    
    
    public Seller findByUsername(String uname){
       return em.createNamedQuery("Seller.findByUsername", Seller.class).setParameter("uname", uname).getSingleResult();
       // return em.createNamedQuery("SELECT s FROM Seller s LEFT JOIN FETCH s.products WHERE s.user.username = :uname", Seller.class).setParameter("uname", uname).getSingleResult();
      
    }
    
    
     
    public void createProductForAuthenticatedSeller(Product product, Seller seller) {
        
        em.persist(product);
        
        
        Seller sellerRef = em.getReference(Seller.class, seller.getId());
        sellerRef.addProduct(product);
        em.merge(sellerRef);
    }

    
   
}