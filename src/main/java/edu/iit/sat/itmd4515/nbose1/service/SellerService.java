/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;

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
}