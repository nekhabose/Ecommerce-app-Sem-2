/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.lab3;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * POJO representing the 'customer' table.
 */
public class Customer {

    @NotNull
    private Integer customerId;

    @NotNull
    @Size(min = 2, max = 45)
    private String firstName;

    @NotNull
    @Size(max = 45)
    private String lastName;

    @Email
    @Size(max = 50)
    private String email;

    @NotNull
    private Integer storeId;

    @NotNull
    private Integer addressId;
    
    @NotNull
    private Boolean active;
    
    @PastOrPresent
    private LocalDate createDate;

    // Getters and setters
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    
    public LocalDate getCreateDate() {
        return createDate;
    }
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
    
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
}
