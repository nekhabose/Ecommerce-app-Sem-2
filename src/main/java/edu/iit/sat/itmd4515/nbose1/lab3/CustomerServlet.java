/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.lab3;

//import jakarta.activation.DataSource;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;
import java.util.logging.Level;
import javax.sql.DataSource;


/**
 *
 * @author Nekha
 */
@WebServlet(name="CustomerServlet",urlPatterns ={"/customer","/cust","/c"})
public class CustomerServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CustomerServlet.class.getName());

    @Resource
    Validator validator;
    
    @Resource(name= "java:app/jdbc/itmd4515DS")
    DataSource ds;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       LOG.info("Inside CustomerServlet.doGet()");
       resp.sendRedirect(req.getContextPath()+ "/customer.jsp");
       
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       LOG.info("Inside CustomerServlet.doPost()");
       
       String custIdParam = req.getParameter("custId");
       String custFirstNameParam = req.getParameter("firstName");
       String custLastNameParam = req.getParameter("lastName");
       String custEmailParam = req.getParameter("email");
       String custAddressIdParam = req.getParameter("addressId");
       String custActiveIdParam = req.getParameter("activeInd");
       String custStoreIdParam = req.getParameter("storeId");
       String custCreateDateParam = req.getParameter("createDate");
       
       LOG.info("custIdParam:\t\t\t\t" + custIdParam);
       LOG.info("custFirstNameParam:\t\t\t\t" + custFirstNameParam);
       LOG.info("custLastNameParam:\t\t\t\t\t" + custLastNameParam);
       LOG.info("custEmailParam:\t\t\t\t" + custEmailParam);
       LOG.info("custAddressIdParam:\t\t\t\t" + custAddressIdParam);
       LOG.info("custActiveIdParam:\t\t\t\t" + custActiveIdParam);
       LOG.info("custStoreIdParam:\t\t\t\t" + custStoreIdParam);
       LOG.info("custCreateDateParam:\t\t\t\t" + custCreateDateParam);
       
       
       Customer customer = new Customer();

       if(custIdParam != null && !custIdParam.isBlank()){
          customer.setCustomerId(Integer.valueOf(custIdParam));
       }
       if(custFirstNameParam != null && !custFirstNameParam.isBlank()){
          customer.setFirstName(custFirstNameParam);
       }
       if(custLastNameParam != null && !custLastNameParam.isBlank()){
          customer.setLastName(custLastNameParam);
       }
       if(custEmailParam != null && !custEmailParam.isBlank()){
          customer.setEmail(custEmailParam);
       }
       if(custAddressIdParam != null && !custAddressIdParam.isBlank()){
          customer.setAddressId (Integer.valueOf(custAddressIdParam));
       }
       if(custStoreIdParam != null && !custStoreIdParam.isBlank()){
          customer.setStoreId(Integer.valueOf(custStoreIdParam));
       }
       if(custCreateDateParam != null && !custCreateDateParam.isBlank()){
          customer.setCreateDate(LocalDate.parse(custCreateDateParam));
       }
       
       if(custActiveIdParam != null && !custActiveIdParam.isBlank()){
          if(custActiveIdParam.equalsIgnoreCase("ON"))
          {
              customer.setActive(true);
          }else{
              customer.setActive(false);
          }
          }
       else{
           customer.setActive(false);
       }
       LOG.info("Built Customer:" + customer.toString());
      
       Set<ConstraintViolation<Customer>>violations = validator.validate(customer);
       
       if (violations.size() > 0)
       {
           LOG.info("Customer has failed validation");
           for (ConstraintViolation<Customer> violation: violations)
       {
           LOG.info(violation.getPropertyPath()+ " " + violation.getMessage());
       }
           req.setAttribute("customer", customer);
           req.setAttribute("violations", violations);
           
           RequestDispatcher rd = req.getRequestDispatcher("customer.jsp");
           rd.forward(req, resp);
           
       }else
       {
            LOG.info("Customer has passed validation");
            insertTestCustomer(customer);
            req.setAttribute("customer", customer);
            
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/conf.jsp");
            rd.forward(req, resp);
 
       }
        
       
    }

   private void insertTestCustomer(Customer customer) {
        String query = "INSERT INTO customer (customer_id, store_id, first_name, last_name, email,address_id,active,create_date)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
               if(ds == null)
                {
                    LOG.severe("Data is Null");
                    
                
                throw new SQLException("Cannot get Connection");
                }
            Connection c = ds.getConnection();
            
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, customer.getCustomerId());
            stmt.setInt(2, customer.getStoreId());
            stmt.setString(3,customer.getFirstName()); 
            stmt.setString(4,customer.getLastName());
            stmt.setString(5,customer.getEmail());
            stmt.setInt(6,customer.getAddressId());
            stmt.setBoolean(7,customer.getActive());
            stmt.setObject(8, customer.getCreateDate());
            stmt.executeUpdate();
            stmt.close();
            c.close();
        }
       catch(SQLException ex)
        {
//            LOG.log(Level.SEVERE, null, ex);
             LOG.log(Level.SEVERE, "Error in inserting data", ex);
        }
        
    }
}