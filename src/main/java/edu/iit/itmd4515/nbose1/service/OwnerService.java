/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.sample.Owner;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author Nekha
 */
@Stateless
public class OwnerService extends AbstractService<Owner>{

    /**
     *
     */
    public OwnerService() {
        super(Owner.class);
    }
    
    /**
     *
     * @return
     */
    public List<Owner> readAll(){
        return super.readAll("Owner.readAll");
    }
    
}

