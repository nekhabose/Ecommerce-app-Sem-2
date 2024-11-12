/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Nekha
 */
@Entity
@NamedQuery(name = "Product.readAll", query = "SELECT p FROM Product p")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @DecimalMin("0.0")
    private BigDecimal price;

    @Temporal(TemporalType.DATE)
    private Date manufactureDate;
    
    @Min(0)
    private Integer stock;
    
    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
    // Constructors

    /**
     *
     */
    public Product() {
    }

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param manufactureDate
     * @param stock
     */
   /* public Product(String name, BigDecimal price, Date manufactureDate, Integer stock) {
        this.name = name;
        this.price = price;
        this.manufactureDate = manufactureDate;
        this.stock = stock;
    }*/
   public Product( String name, BigDecimal price, Date manufactureDate, Integer stock) {
        
        this.name = name;
        this.price = price;
        this.manufactureDate = manufactureDate;
        this.stock = stock;
     
    }

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
    public BigDecimal getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     *
     * @return
     */
    public Date getManufactureDate() {
        return manufactureDate;
    }

    /**
     *
     * @param manufactureDate
     */
    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    /**
     *
     * @return
     */
    public Integer getStock() {
        return stock;
    }

    /**
     *
     * @param stock
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    // Getter and Setter for the Seller field
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
     
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Product{" + 
                "id=" + id + 
                ", name='" + name + '\'' + 
                ", price=" + price + 
                ", manufactureDate=" + manufactureDate + 
                ", stock=" + stock + 
                ", seller=" + (seller != null ? seller.getStoreName() : "No Seller") + 
                '}';
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final Product other = (Product) obj;
        if (this.id == null || other.id == null)
        {
        return false;      
        }
        
        return Objects.equals(this.id, other.id);
    }
    
    
    
    
}
