/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.sample;

import edu.iit.itmd4515.nbose1.security.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nekha
 */
@Entity
@NamedQuery(name = "Vet.readAll", query = "select v from Vet v")

@Table(name = "vet")
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // The primary key

    // Other fields for the Vet class
    @NotBlank
    private String name;
    
     /**
     * OneToOne uni-directional relationship*
     * Vet is the owning side (and ONLY) side.
     */
    @OneToOne
    @JoinColumn(name = "VET_SPECIALIZATION")
    private Specilization specilization;
    
    /**
     * ManyToOne / OneToMany bi-directional relationship*
     * Appt is the owning side
     * Vet is the inverse side
     * 
     * This is the inverse side
     */

    @OneToMany(mappedBy ="vet")
    private List<Appointment> appointments= new ArrayList<>();

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
     *
     */
    public Vet()
    {
    }
    
    @OneToOne
    @JoinColumn(name = "USERNAME")
    private User user;

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @param name
     */
    public Vet(String name) {
        this.name = name;
        
    }

    /**
     *
     * @return
     */
    public Specilization getSpecilization() {
        return specilization;
    }

    /**
     *
     * @param specilization
     */
    public void setSpecilization(Specilization specilization) {
        this.specilization = specilization;
    }

    

    // Getters and setters

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
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
        final Vet other = (Vet) obj;
        if (this.id == null || other.id == null)
        {
          return false;  
        }
        return Objects.equals(this.id, other.id);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Vet{" + "id=" + id + ", name=" + name + ", specilization=" + specilization + '}';
    }

   
}

