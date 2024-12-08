/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.security;

import edu.iit.sat.itmd4515.nbose1.service.AbstractService;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 *Based on the pattern used in the instructor’s example from Lab 8 materials.
 * @author Nekha
 */

@Stateless
public class UserService extends AbstractService<User> {

    private static final Logger LOG = Logger.getLogger(UserService.class.getName());

    public UserService() {
        super(User.class);
    }

    /**
     * Finds a User by their username.
     *
     * @param username the username to search for
     * @return the User entity if found, otherwise null
     */
    public User findByUsername(String username) {
        LOG.info("Searching for user with username: " + username);
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOG.warning("No user found with username: " + username);
            return null;
        }
    }
      /**
     * Retrieves all users.
     *
     * @return list of all User entities
     */
    public List<User> readAllUsers() {
        LOG.info("Retrieving all users.");
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }
}