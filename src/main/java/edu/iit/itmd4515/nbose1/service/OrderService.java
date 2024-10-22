/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.nbose1.domain.Order;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author Nekha
 */
@Stateless
public class OrderService extends AbstractService<Order> {

    public OrderService() {
        super(Order.class);
    }

    public List<Order> readAll() {
        return super.readAll("Order.readAll");
    }
}
