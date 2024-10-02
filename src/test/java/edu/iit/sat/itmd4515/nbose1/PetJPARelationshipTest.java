/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1;

import edu.iit.sat.itmd4515.sample.Owner;
import edu.iit.sat.itmd4515.sample.Pet;
import edu.iit.sat.itmd4515.sample.PetType;
import edu.iit.sat.itmd4515.sample.Specilization;
import edu.iit.sat.itmd4515.sample.Vet;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nekha
 */
public class PetJPARelationshipTest extends AbstractJPATest{
    
    @Test
    public void uniDirectionalTestCase()
    {
        Specilization spec = new Specilization("Emergency");
        Vet v = new Vet("Emergency Vet");
        v.setSpecilization(spec);
        
        tx.begin();
        em.persist(spec);
        em.persist(v);
        tx.commit();
        
        // can assert by reading  things back and checking
        Vet readBackFromDatabase = em.find(Vet.class, v.getId());
        assertNotNull(readBackFromDatabase.getSpecilization());
        assertEquals("Emergency",readBackFromDatabase.getSpecilization().getName());
    }
    
    @Test
    public void biDirectionalTestCase()
    {
      Owner o= new Owner("Nekha","nekha@iit.edu");
      Pet p= new Pet("Momo", LocalDate.of(2022,10,17),PetType.CAT);
      //Case #1- relationship structure is defined by ORM annotations.
      // and data is persisted through EM, with non-owning entity first.
      // No relationships are set. What happens?
      //Answer- the association is not written to the database, nothing in the join table
      
      //Case #2-set the non-owning side of the relationship, but not the owning side.
      //What happens?
      //p.getOwners().add(o);
      //Answer -the same thing, the association is not written to the database, nothing in the join table.
      //The OWNING side controls the database update.If you dont update the owning side, then the database isnt updated.
      
      //Case #3-Set the OWNING side, but skip the non-owning side.
      //What happen?
      //o.getPets().add(p);
      //Answer we do see data in the join table now, because we updated from the owning side.
      //To completely illustrate this case, we also need to Navigate the relationsip in our application.
      //After navigation, we see that the collection is populated on the owning side, but NOT on the inverse side.
      //We have NOT managed BOTH sides of the relationship yet.
      
      //Case #4-properly manage both sides of the relationship
      //o.getPets().add(p);
      //p.getOwners().add(o);
      //Answer - Success.This is teh correct way to manage both sides of the relationship
      //BUT - we need to talk about helper methods.
      o.addOwnerPet(p);
      
      tx.begin();
      em.persist(p);
      em.persist(o);
      tx.commit();
      
      System.out.println("Navigating M:M bidirectional Relationship from owning side:\t"+o.getPets().toString());
      System.out.println("Navigating M:M bidirectional Relationship from inverse side:\t"+p.getOwners().toString());
      
      Owner readBackFromDatabase = em.find(Owner.class, o.getId());
      assertTrue(o.getPets().size() == 1);
      assertTrue(p.getOwners().size() ==1);
      
      // I will demo the cleanup in this test case, so that I can show you the remove helper method as well
      tx.begin();
      //first unset any relationships before removing data
      o.removeOwnerPet(p);
      //removing non-owning entity first
      em.remove(p);
      //and then owning entity
      em.remove(o);
      tx.commit();
    }
    
}
    
    

