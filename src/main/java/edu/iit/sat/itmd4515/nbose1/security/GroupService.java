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
 * Based on the pattern used in the instructor’s example from Lab 8 materials.
 * @author Nekha
 */

@Stateless
public class GroupService extends AbstractService<Group> {

    private static final Logger LOG = Logger.getLogger(GroupService.class.getName());

    public GroupService() {
        super(Group.class);
    }

    /**
     * Finds a Group by its group name.
     *
     * @param groupName the name of the group to search for
     * @return the Group entity if found, otherwise null
     */
    public Group findByGroupName(String groupName) {
        LOG.info("Searching for group with name: " + groupName);
        TypedQuery<Group> query = em.createQuery("SELECT g FROM Group g WHERE g.groupName = :groupName", Group.class);
        query.setParameter("groupName", groupName);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOG.warning("No group found with name: " + groupName);
            return null;
        }
    }

    /**
     * Retrieves all groups.
     *
     * @return list of all Group entities
     */
    public List<Group> readAllGroups() {
        LOG.info("Retrieving all groups.");
        TypedQuery<Group> query = em.createQuery("SELECT g FROM Group g", Group.class);
        return query.getResultList();
    }
}