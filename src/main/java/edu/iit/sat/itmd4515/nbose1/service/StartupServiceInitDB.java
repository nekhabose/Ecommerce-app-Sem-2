/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.sample.Appointment;
import edu.iit.sat.itmd4515.sample.Owner;
import edu.iit.sat.itmd4515.sample.Pet;
import edu.iit.sat.itmd4515.sample.PetType;
import edu.iit.sat.itmd4515.sample.Specilization;
import edu.iit.sat.itmd4515.sample.Vet;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

/**
 *
 * @author Nekha
 */
@Startup
@Singleton
public class StartupServiceInitDB {

    private static final Logger LOG = Logger.getLogger(StartupServiceInitDB.class.getName());

    @EJB SpecilizationService specSvc;
    @EJB PetService petSvc;
    @EJB VetService vetSvc;
    @EJB OwnerService ownerSvc;
    @EJB ApptService apptSvc;
    
    public StartupServiceInitDB() {
    }
    
    @PostConstruct
    private void postConstruct(){
        LOG.info("Inside StartupServiceInitDB.postConstruct()");
        
        // When using JPA, create non-owning entities first
        Specilization s1 = new Specilization("Canine");
        Specilization s2 = new Specilization("Feline");
        Specilization s3 = new Specilization("Emergency");
        
        specSvc.create(s1);
        specSvc.create(s2);
        specSvc.create(s3);
        
        Pet p1 = new Pet("Appa", LocalDate.of(2022, 10, 1), PetType.CAT);
        Pet p2 = new Pet("Momo", LocalDate.of(2022, 10, 1), PetType.CAT);
        Pet p3 = new Pet("Mr Ninja", LocalDate.of(2012, 5, 12), PetType.CAT);
        Pet p4 = new Pet("Otis", LocalDate.of(2020, 9, 27), PetType.DOG);
        Pet p5 = new Pet("KK", LocalDate.of(2015, 7, 14), PetType.DOG);
        
        petSvc.create(p1);
        petSvc.create(p2);
        petSvc.create(p3);
        petSvc.create(p4);
        petSvc.create(p5);
        
        // then, we start creating entities that OWN relationships
        Vet v1 = new Vet("Cat Doctor");
        v1.setSpecilization(s2);
        Vet v2 = new Vet("Dog Doctor");
        v2.setSpecilization(s1);
        Vet v3 = new Vet("Emergency Vet");
        v3.setSpecilization(s3);
        
        vetSvc.create(v1);
        vetSvc.create(v2);//setName
        vetSvc.create(v3);
        
        Owner o1 = new Owner("Cat Owner", "scotts@iit.edu");
        o1.addOwnerPet(p1);
        o1.addOwnerPet(p2);
        o1.addOwnerPet(p3);
        
        Owner o2 = new Owner("Dog Owner", "dogperson@iit.edu");
        o2.addOwnerPet(p4);
        o2.addOwnerPet(p5);
        
        ownerSvc.create(o1);
        ownerSvc.create(o2);
        
        Appointment a1 = new Appointment(LocalDate.of(2024, 12, 20), LocalTime.of(9, 30));
        a1.schedAppt(o1, p3, v1);
        Appointment a2 = new Appointment(LocalDate.of(2025, 2, 12), LocalTime.of(15, 30));
        a2.schedAppt(o2, p5, v2);
        Appointment a3 = new Appointment(LocalDate.of(2024, 11, 12), LocalTime.of(15, 30));
        a3.schedAppt(o2, p4, v3);
        
        apptSvc.create(a1);
        apptSvc.create(a2);
        apptSvc.create(a3);
        
        LOG.info("-----------------------------------------------------------------------------");
        LOG.info("DEMONSTRATING output section for your relatinoships");
        LOG.info("-----------------------------------------------------------------------------\n");
        for( Vet v : vetSvc.readAll() ){
        LOG.info("-----------------------------------------------------------------------------");
            LOG.info(v.toString());
            
            LOG.info("\tUnidirectional relationship from Vet (owner) to Specialization >>>>>>>>>");
            LOG.info("\t" + v.getSpecilization().toString());
            
            LOG.info("\n");
            
            LOG.info("\tBi-directional relationship from Vet (inverse) to Appointment >>>>>>>>>");
            for( Appointment a : v.getAppointments()){
                LOG.info("\t" + a.toString());
            }            
        }
        
    }
    
}
