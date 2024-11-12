/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.domain;

/**
 *
 * @author Nekha
 */
import edu.iit.sat.itmd4515.nbose1.security.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nekha
 */
@Entity
@NamedQuery(name = "Customer.readAll", query = "SELECT c FROM Customer c")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Size(min = 5, max = 50)
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // Constructors

    /**
     *
     */
    public Customer() {
    }

    /**
     *
     * @param name
     * @param email
     */
    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
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

    // Getters and Setters

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
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     *
     * @param orders
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     *
     * @param order
     */
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

    /**
     *
     * @param order
     */
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + '}';
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Customer other = (Customer) obj;
        return Objects.equals(this.id, other.id);
    }

   
    
}
