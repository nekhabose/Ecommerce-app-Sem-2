/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;

import edu.iit.sat.itmd4515.sample.Pet;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.logging.Logger;

/**
 *
 * @author Nekha
 */
@Named
@RequestScoped
public class PetController {
    
    private static final Logger LOG = Logger.getLogger(PetController.class.getName());
    
    private Pet pet;

    /**
     *
     */
    public PetController()
    {}
    
    @PostConstruct
    private void postConstruct()
    
    {
        LOG.info("Inside PetController.postConstruct()");
        pet = new Pet();
    }
    
    /**
     *
     * @return
     */
    public String savePet()
    {
     LOG.info("Inside PetController.savePet() with" + pet.toString());   
     
     return "createPetConfirmation.xhtml";
    }

    /**
     *
     * @return
     */
    public Pet getPet() {
        return pet;
    }

    /**
     *
     * @param pet
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    
}
