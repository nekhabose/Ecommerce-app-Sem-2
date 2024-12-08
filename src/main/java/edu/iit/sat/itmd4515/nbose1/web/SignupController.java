/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package edu.iit.sat.itmd4515.nbose1.web;

import edu.iit.sat.itmd4515.nbose1.domain.Customer;
import edu.iit.sat.itmd4515.nbose1.domain.Seller;
import edu.iit.sat.itmd4515.nbose1.security.Group;
import edu.iit.sat.itmd4515.nbose1.security.GroupService;
import edu.iit.sat.itmd4515.nbose1.security.User;
import edu.iit.sat.itmd4515.nbose1.security.UserService;
import edu.iit.sat.itmd4515.nbose1.service.CustomerService;
import edu.iit.sat.itmd4515.nbose1.service.SellerService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Managed Bean to handle user sign-up for both Sellers and Customers.
 * 
 * @author 
 */
@Named
@RequestScoped
public class SignupController {

    private static final Logger LOG = Logger.getLogger(SignupController.class.getName());

    @EJB
    private UserService userSvc;

    @EJB
    private GroupService groupSvc;

    @EJB
    private SellerService sellerSvc; 

    @EJB
    private CustomerService customerSvc; 
    // Form Fields
    private String username;
    private String password;
    private String name;
    private String email;
    private String userType; // "SELLER" or "CUSTOMER"

    // Validator
    private Validator validator;

    @PostConstruct
    public void postConstruct() {
        LOG.info("Initializing SignupController...");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Action method to handle user sign-up.
     *
     * @return Navigation outcome
     */
    public String doSignup() {
        LOG.info("Attempting to sign up user: " + username + " as " + userType);

        FacesContext context = FacesContext.getCurrentInstance();

        // Basic validation
        if (userType == null || userType.isEmpty()) {
            context.addMessage("userType", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account Type Required", "Please select an account type."));
            return null;
        }

        // Check if username already exists
        if (userSvc.findByUsername(username) != null) {
            context.addMessage("username", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username Exists", "A user with this username already exists."));
            return null;
        }

        // Create User entity
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); 

        // Validate User entity
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, violation.getPropertyPath() + ": " + violation.getMessage(), null));
            }
            return null;
        }

        // Assign group based on userType
        String groupName = "";
        if ("SELLER".equalsIgnoreCase(userType)) {
            groupName = "SELLER_GROUP";
        } else if ("CUSTOMER".equalsIgnoreCase(userType)) {
            groupName = "CUSTOMER_GROUP";
        } else {
            context.addMessage("userType", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Account Type", "Selected account type is invalid."));
            return null;
        }

        Group group = groupSvc.findByGroupName(groupName);
        if (group == null) {
            context.addMessage("userType", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Group Not Found", "Selected group does not exist."));
            return null;
        }

        user.addGroup(group);
        userSvc.create(user);
        LOG.info("User created successfully with username: " + username + " in group: " + groupName);

        // Create corresponding Seller or Customer entity
        if ("SELLER".equalsIgnoreCase(userType)) {
            Seller seller = new Seller();
            seller.setStoreName(name); // Assuming 'name' is the store name for sellers
            seller.setEmail(email);
            seller.setUser(user);
            sellerSvc.create(seller); // Use SellerService to persist
            LOG.info("Seller entity created for user: " + username);
        } else if ("CUSTOMER".equalsIgnoreCase(userType)) {
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setUser(user);
            customerSvc.create(customer); // Use CustomerService to persist
            LOG.info("Customer entity created for user: " + username);
        }

        // Add success message
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sign-Up Successful", "Your account has been created. Please login."));

        // Redirect to login page
        return "login.xhtml?faces-redirect=true";
    }
}
