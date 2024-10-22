/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.sample;

import jakarta.persistence.Entity;

/**
 *
 * @author Nekha
 */
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "Owner.readAll", query = "select o from Owner o")

@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key
    
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    
    /**
     * ManyToMany bi-directional relationship*
     * Owner is the owning side
     * Pet is the inverse side
     * 
     * This is the owning side.
     */
    
    @ManyToMany
    @JoinTable(name = "OWNER_PETS",
               joinColumns = @JoinColumn(name = "OWNER_ID"),
               inverseJoinColumns = @JoinColumn(name = "PET_ID"))
    private List<Pet> pets= new ArrayList<>();
    
     /**
     * ManyToOne/ OneToMany bi-directional relationship*
     * appt is the owning side
     * Owner is the inverse side
     * 
     * This is the inverse side.
     */
    
    @OneToMany(mappedBy = "owner")
    private List<Appointment> appointments = new ArrayList<>();

    /**
     * Get the value of appointments
     *
     * @return the value of appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Set the value of appointments
     *
     * @param appointments new value of appointments
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }


    /**
     * Get the value of pets
     *
     * @return the value of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Set the value of pets
     *
     * @param pets new value of pets
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Owner()
    {
    }
   
    public Owner(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    
    
    public void addOwnerPet(Pet p)
    {
        if(!this.pets.contains(p))
        {
            this.pets.add(p);
        }
        if (!p.getOwners().contains(this))
        {
            p.getOwners().add(this);
       }
    }
    public void removeOwnerPet(Pet p)
    {
        if(!this.pets.contains(p))
        {
            this.pets.remove(p);
        }
        if (!p.getOwners().contains(this))
        {
            p.getOwners().remove(this);
       }
    }
    
    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    // Getters and setters
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final Owner other = (Owner) obj;
         if (this.id == null || other.id == null)
        {
          return false;  
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Owner{" + "id=" + id + ", name=" + name + ", email=" + email + ", pets=" + pets + '}';
    }
    
    
}
