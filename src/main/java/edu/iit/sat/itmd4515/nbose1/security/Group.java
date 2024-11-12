/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Based on the pattern used in the instructor’s example from Lab 8 materials.
 * @author Nekha
 */
@Entity
@Table(name = "SEC_GROUP")
@NamedQuery(name = "Group.findAll", query = "select g from Group g")
public class Group {
   
    @Id
    private String groupName;
    
    private String groupDesc;
    
    
    @ManyToMany(mappedBy = "groups")
    private List<User> users = new ArrayList<>();

    /**
     *
     * @param groupName
     * @param groupDesc
     */
    public Group(String groupName, String groupDesc) {
        this.groupName = groupName;
        this.groupDesc = groupDesc;
    }

    /**
     *
     */
    public Group() {
    }
    

    /**
     * Get the value of users
     *
     * @return the value of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Set the value of users
     *
     * @param users new value of users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }


    /**
     * Get the value of groupDesc
     *
     * @return the value of groupDesc
     */
    public String getGroupDesc() {
        return groupDesc;
    }

    /**
     * Set the value of groupDesc
     *
     * @param groupDesc new value of groupDesc
     */
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }


    /**
     * Get the value of groupName
     *
     * @return the value of groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Set the value of groupName
     *
     * @param groupName new value of groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.groupName);
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
        final Group other = (Group) obj;
        return Objects.equals(this.groupName, other.groupName);
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Group{" + "groupName=" + groupName + ", groupDesc=" + groupDesc + '}';
    }

}
