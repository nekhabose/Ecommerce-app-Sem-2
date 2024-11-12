/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.security;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nekha
 */
@Entity
@Table(name = "SEC_USER")
@EntityListeners(UserPasswordHash.class)
@NamedQuery(name = "User.findAll", query = "select u from User u")
public class User {
    
    @Id
    private String username;   
    private String password;
    
    
    @ManyToMany
    @JoinTable(name = "SEC_USER_GROUPS",
    joinColumns = @JoinColumn(name= "USERNAME"),
    inverseJoinColumns = @JoinColumn( name= "GROUPNAME"))
           
    private List<Group> groups= new ArrayList<>();

    /**
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *
     */
    public User() {
    }
    
    /**
     *
     * @param g
     */
    public void addGroup(Group g)
    {
        this.groups.add(g);
        g.getUsers().add(this);
    }
    
    /**
     *
     * @param g
     */
    public void removeGroup(Group g)
    {
        this.groups.remove(g);
        g.getUsers().remove(this);
        
    }
    
    

    /**
     * Get the value of groups
     *
     * @return the value of groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Set the value of groups
     *
     * @param groups new value of groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     *Based on the pattern used in the instructor’s example from Lab 8 materials.
     * @return
     */
    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", groups=" + groups + '}';
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.username);
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
        final User other = (User) obj;
        return Objects.equals(this.username, other.username);
    }

    
}
