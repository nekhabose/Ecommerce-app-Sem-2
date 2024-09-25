/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Nekha
 */
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @DecimalMin("0.0")
    private BigDecimal price;

    @Temporal(TemporalType.DATE)
    private Date manufactureDate;
    
    @Min(0)
    private Integer stock;

    // Constructors
    public Product() {
    }

    public Product(String name, BigDecimal price, Date manufactureDate, Integer stock) {
        this.name = name;
        this.price = price;
        this.manufactureDate = manufactureDate;
        this.stock = stock;
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

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }
     public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }
    public Integer getStock() {
        return stock;
    }
     public void setStock(Integer stock) {
        this.stock = stock;
    }
     
    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + ", manufactureDate=" + manufactureDate + ", stock=" + stock + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final Product other = (Product) obj;
        if (this.id == null || other.id == null)
        {
            
        }
        
        return Objects.equals(this.id, other.id);
    }
    
    
    
    
}
