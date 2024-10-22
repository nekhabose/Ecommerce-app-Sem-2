/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.sample;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nekha
 */
@Entity
@NamedQuery(name = "Pet.readAll", query = "select p from Pet p")

public class Pet {
       
       
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       
       @NotBlank
       @Column(nullable = false, name ="pet_name")
       @NotNull
       private String name;
       
       @PastOrPresent
       private LocalDate birthDate;
       
       @Enumerated(EnumType.STRING)
       private PetType type;
       
     /**
     * ManyToMany bi-directional relationship*
     * Owner is the owning side
     * Pet is the inverse side
     * 
     * This is the inverse side
     */
       
       
       
       @ManyToMany(mappedBy = "pets")
       private List<Owner> owners = new ArrayList<>();;
       

     
       public Pet() {
      }
    

    /**
     * Get the value of owners
     *
     * @return the value of owners
     */
    public List<Owner> getOwners() {
        return owners;
    }

    /**
     * Set the value of owners
     *
     * @param owners new value of owners
     */
    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }


    public Pet(String name, LocalDate birthDate, PetType type) {
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
    }
    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }
 
     
    public String getName() {
        return name;
    }

   
    public void setName(String name) {
        this.name = name;
    }  
     
    public LocalDate getBirthDate() {
        return birthDate;
    }

    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    
    public PetType getType() {
        return type;
    }

    
    public void setType(PetType type) {
        this.type = type;
    }

       
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pet other = (Pet) obj;
        
        // if we are using GeneratedValue, we need one more short circuit
        // ifthe Id is null, return false
        if (this.id == null || other.id == null)
        {
            
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Pet{" + "id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", type=" + type + '}';
    }    
}
