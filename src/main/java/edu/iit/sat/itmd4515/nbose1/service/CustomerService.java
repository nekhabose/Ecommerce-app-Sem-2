

 /* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;

import edu.iit.sat.itmd4515.nbose1.domain.Customer;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.logging.Logger;
/**
 *
 * @author Nekha
 */

@Stateless
public class CustomerService extends AbstractService<Customer> {

    private static final Logger LOG = Logger.getLogger(CustomerService.class.getName());

    /**
     * Default constructor
     */
    public CustomerService() {
        super(Customer.class);
    }

    /**
     * Reads all customers
     * 
     * @return List of all customers
     */
    public List<Customer> readAll() {
        return super.readAll("Customer.readAll");
    }

    /**
     * Finds a customer by username
     * 
     * @param uname the username of the customer
     * @return the Customer object or null if not found
     * @throws IllegalStateException if more than one customer is found with the same username
     */
    public Customer findByUsername(String uname) {
        LOG.info("Finding customer with username: " + uname);

        List<Customer> customers = em.createNamedQuery("Customer.findByUsername", Customer.class)
                                     .setParameter("uname", uname)
                                     .getResultList();

        if (customers.isEmpty()) {
            LOG.warning("No customer found with username: " + uname);
            return null; // No customer found
        }

        return customers.get(0); // Return the single result
    }
}
