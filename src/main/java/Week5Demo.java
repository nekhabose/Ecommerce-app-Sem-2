
import edu.iit.sat.itmd4515.nbose1.domain.Pet;
import edu.iit.sat.itmd4515.nbose1.domain.PetType;
import edu.iit.sat.itmd4515.nbose1.domain.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
//import java.util.Date;
import java.sql.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nekha
 */
public class Week5Demo {
    
    public static void main(String... args)
    {
//        Pet pet = new Pet("Fluffykins", LocalDate.of(2020,12,12), PetType.CAT);
//        //pet.setName("Fluffykins");
//        
//        System.out.println("Before persist:" + pet.toString());
//        
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("itmd4515testPU");
//        EntityManager em = emf.createEntityManager();
//        
//        //Pet pet = new Pet(999l,"Fluffykins");
//        
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        em.persist(pet);
//        pet.setName("Super Fluffykins");
//        tx.commit();
//        
//        System.out.println("After persist:" + pet.toString());
//        
        
       
        Product product = new Product("Laptop", new BigDecimal("999.99"), new Date(System.currentTimeMillis()), 50);

       
        System.out.println("Before persist: " + product.toString());

        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("itmd4515testPU");
        EntityManager em = emf.createEntityManager();

       
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        
        em.persist(product);

        
         product.setName("Superrr Laptop");
        
        
        tx.commit();

        
        System.out.println("After persist: " + product.toString());

       
        em.close();
        emf.close();
    }
        
    }
    

