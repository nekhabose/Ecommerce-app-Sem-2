/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.nbose1.domain.OrderLine;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author Nekha
 */
@Stateless
public class OrderLineService extends AbstractService<OrderLine> {

    public OrderLineService() {
        super(OrderLine.class);
    }

    public List<OrderLine> readAll() {
        return super.readAll("OrderLine.readAll");
    }
}
