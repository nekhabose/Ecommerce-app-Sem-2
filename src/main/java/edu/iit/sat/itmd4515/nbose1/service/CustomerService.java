package edu.iit.sat.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.nbose1.domain.Customer;
import jakarta.ejb.Stateless;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nekha
 */
@Stateless
public class CustomerService extends AbstractService<Customer> {

    /**
     *
     */
    public CustomerService() {
        super(Customer.class);
    }

    /**
     *
     * @return
     */
    public List<Customer> readAll() {
        return super.readAll("Customer.readAll");
    }
}
