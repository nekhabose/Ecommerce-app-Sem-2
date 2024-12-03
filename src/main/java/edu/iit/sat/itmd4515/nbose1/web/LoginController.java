/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.web;

import edu.iit.sat.itmd4515.nbose1.security.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import static jakarta.security.enterprise.AuthenticationStatus.NOT_DONE;
import static jakarta.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static jakarta.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static jakarta.security.enterprise.AuthenticationStatus.SUCCESS;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nekha
 */
@Named
@RequestScoped
public class LoginController {

    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    @Inject FacesContext facesContext;
    @Inject SecurityContext securityContext;

    // Model
    
private User user;
    /**
     *
     */
    public LoginController() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("Inside LoginController.postConstruct() yes");
        user = new User();
    }

    // helper method
    
    /**
     *
     * @return
     */
    public String getAuthenticatedUsername(){
        return securityContext.getCallerPrincipal().getName();
        
    }
    
    public boolean isCustomer() {
        LOG.info("Checking CUSTOMER_ROLE: " + securityContext.isCallerInRole("CUSTOMER_ROLE"));
        return securityContext.isCallerInRole("CUSTOMER_ROLE");
    }

    public boolean isSeller() {
        LOG.info("Checking SELLER_ROLE: " + securityContext.isCallerInRole("SELLER_ROLE"));
        return securityContext.isCallerInRole("SELLER_ROLE");
    }

    public boolean isAdmin() {
        LOG.info("Checking ADMIN_ROLE: " + securityContext.isCallerInRole("ADMIN_ROLE"));
        return securityContext.isCallerInRole("ADMIN_ROLE");
    }
    // action methods

    /**
     *Based on the pattern used in the instructor’s example from Lab 8 materials.
     * @return
     */
    public String doLogin() {
        
        LOG.info("Attempting login for user: " + user.getUsername());

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        Credential cred = new UsernamePasswordCredential(this.user.getUsername(), new Password(this.user.getPassword()));

        AuthenticationStatus status = securityContext.authenticate(request, response, AuthenticationParameters.withParams().credential(cred));

        switch(status){
            case SUCCESS:
                LOG.info(status.toString());
                  return "/welcome.xhtml";
                  
            case SEND_FAILURE:
                LOG.info("FAILURE! "+ status.toString());
                return "/error.xhtml";
            case NOT_DONE:
                LOG.info("NOT DONE! " + status.toString());
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login not completed", "Please try again."));
                return "/error.xhtml";
            case SEND_CONTINUE:
                LOG.info(status.toString());
                break;
        }
       
     
        
      return "/welcome.xhtml?faces-redirect=true";
       
    }

    /**
     *
     * @return
     */
    public String doLogout() {
        LOG.info("LoginController.doLogout() called");
        
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
       
       return "/login.xhtml?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
