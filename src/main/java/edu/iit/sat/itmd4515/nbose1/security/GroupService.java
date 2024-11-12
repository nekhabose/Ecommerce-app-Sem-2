/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.security;

import edu.iit.sat.itmd4515.nbose1.service.AbstractService;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 * Based on the pattern used in the instructor’s example from Lab 8 materials.
 * @author Nekha
 */

@Stateless
public class GroupService extends AbstractService <Group> {
    
    /**
     *
     */
    public GroupService()
   {
     super(Group.class);
     
   }

    /**
     *
     * @return
     */
    public List<Group> readAll()
    {
        return super.readAll("Group.findAll");
    }
   
    
}
