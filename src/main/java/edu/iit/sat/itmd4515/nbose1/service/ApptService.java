/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.sample.Appointment;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author Nekha
 */
@Stateless
public class ApptService extends AbstractService<Appointment>{

    public ApptService() {
        super(Appointment.class);
    }

    public List<Appointment> readAll(){
        return super.readAll("Appt.readAll");
    }
    
}

