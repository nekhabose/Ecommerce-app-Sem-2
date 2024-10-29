/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package edu.iit.sat.itmd4515.sample;

/**
 *
 * @author Nekha
 */
public enum PetType {
    
    CAT("Catiee"),
    DOG("Dogiee"),
    BIRD("Birdiee");
    
        private String label;

    private PetType(String label) {
        this.label = label;
    }  
        
        
    public String getLabel() {
        return label;
    }

    
    
   

    
}
