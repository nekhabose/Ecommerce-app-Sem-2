/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.sample.Vet;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author Nekha
 */
@Stateless
public class VetService extends AbstractService<Vet>{

    public VetService() {
        super(Vet.class);
    }
    
    public List<Vet> readAll(){
        return super.readAll("Vet.readAll");
    }
    
}

