/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.itmd4515.nbose1.security;

import edu.iit.itmd4515.nbose1.service.AbstractService;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author Nekha
 */

@Stateless
public class UserService extends AbstractService <User> {
    
   public UserService()
   {
     super(User.class);
     
   }
    public List<User> readAll()
    {
        return super.readAll("User.findAll");
    }
   
    
}
