/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;
import edu.iit.sat.itmd4515.nbose1.security.Group;
import edu.iit.sat.itmd4515.nbose1.security.GroupService;
import edu.iit.sat.itmd4515.nbose1.security.User;
import edu.iit.sat.itmd4515.nbose1.security.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Nekha
 */




@Named
@RequestScoped
public class AdminUserController {
    
    private static final Logger LOG = Logger.getLogger(AdminUserController.class.getName());

    @EJB
    private UserService userSvc;

    @EJB
    private GroupService groupSvc;

    private User newUser;
    private String selectedGroup;

    private List<Group> groups;

    @PostConstruct
    public void postConstruct() {
        LOG.info("Initializing AdminUserController...");
        newUser = new User();
        groups = groupSvc.readAllGroups();
    }

    // Getters and Setters
    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public String getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(String selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public List<Group> getGroups() {
        return groups;
    }

    // Action Method
    public String createUser() {
        LOG.info("Creating new user: " + newUser.getUsername() + " in group: " + selectedGroup);
        if (userSvc.findByUsername(newUser.getUsername()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "User Exists", "A user with this username already exists."));
            return null;
        }
        Group group = groupSvc.findByGroupName(selectedGroup);
        if (group == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Group", "Selected group does not exist."));
            return null;
        }
        newUser.addGroup(group);
        userSvc.create(newUser);
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "User Created", "User has been successfully created."));
        return "adminDashboard.xhtml?faces-redirect=true";
    }
}
