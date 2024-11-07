/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.nbose1.domain.Product;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import java.util.List;

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

    /**
     *
     * @return
     */
    public List<Product> readAll() {
        return super.readAll("Product.readAll");
    }
}
