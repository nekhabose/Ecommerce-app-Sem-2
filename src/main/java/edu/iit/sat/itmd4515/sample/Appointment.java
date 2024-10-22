/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.sample;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 *
 * @author Nekha
 */
//itmd4515testPU
@Entity
@NamedQuery(name = "Appt.readAll", query = "select a from Appointment a")

public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @FutureOrPresent
    private LocalDate date;
    
    private LocalTime time;
    
     /**
     * ManyToOne / OneToMany bi-directional relationship*
     * Appt is the owning side
     * Owner is the inverse side
     * 
     * This is the owning side
     */
    
    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Owner owner;
    
    /**
     * ManyToOne / OneToMany bi-directional relationship*
     * Appt is the owning side
     * Vet is the inverse side
     * 
     * This is the owning side
     */
    @ManyToOne
    @JoinColumn(name = "VET_ID")
    private Vet vet;
    
    /**
     * ManyToOne / uni-directional relationship*
     * Appt is the owning side
    
     */
    
    @ManyToOne
    @JoinColumn(name = "PET_ID")
    private Pet pet;
    

    
    public Owner getOwner() {
        return owner;
    }
   

    
    public Pet getPet() {
        return pet;
    }

    
    public void setPet(Pet pet) {
        this.pet = pet;
    }


    
    public Vet getVet() {
        return vet;
    }

    
    public void setVet(Vet vet) {
        this.vet = vet;
    }


   
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    
  
    
    public Appointment()
    {
    }

    public Appointment(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }
     public void schedAppt(Owner o, Pet p, Vet v) {
        this.owner = o;
        this.vet = v;
        this.pet = p;

        // manage bi-directional relatinoships
        if (!o.getAppointments().contains(this)) {
            o.getAppointments().add(this);
        }
        if (!v.getAppointments().contains(this)) {
            v.getAppointments().add(this);
        }
    }

    public void cancelAppt() {

        if (this.owner.getAppointments().contains(this)) {
            this.owner.getAppointments().remove(this);
        }
        if (this.vet.getAppointments().contains(this)) {
            this.vet.getAppointments().remove(this);
        }

        this.owner = null;
        this.vet = null;
        this.pet = null;
    }
   
    /**
     * Get the value of time
     *
     * @return the value of time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Set the value of time
     *
     * @param time new value of time
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }


    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Appointment other = (Appointment) obj;
         if (this.id == null || other.id == null)
        {
          return false;  
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Appointment{" + "id=" + id + ", date=" + date + ", time=" + time + ", owner=" + owner + ", vet=" + vet + ", pet=" + pet + '}';
    }
    
    
    
}

