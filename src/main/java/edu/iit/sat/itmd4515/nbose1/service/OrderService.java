/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.service;
import edu.iit.sat.itmd4515.nbose1.domain.Order;
import edu.iit.sat.itmd4515.nbose1.domain.OrderLine;
import edu.iit.sat.itmd4515.nbose1.domain.Product;
import edu.iit.sat.itmd4515.nbose1.domain.Customer;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class OrderService extends AbstractService<Order> {

    private static final Logger LOG = Logger.getLogger(OrderService.class.getName());

    public OrderService() {
        super(Order.class);
    }

    public List<Order> readAll() {
        return super.readAll("Order.readAll");
    }

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    /**
     * Updated method to find cart by customer ID instead of the entire customer object.
     */
    public Order findCartByCustomer(Customer customer) {
        try {
            TypedQuery<Order> query = em.createQuery(
                "SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.status = 'CART'", Order.class);
            query.setParameter("customerId", customer.getId());
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("No cart found for customer ID: " + customer.getId());
            return null;
        }
    }



    @Transactional
    public void addToCart(Customer customer, Product product, int quantity) throws InsufficientStockException {
        LOG.info("Adding to cart: Customer " + customer.getId() + ", Product " + product.getId() + ", Quantity " + quantity);
        try {
            Order cart = findCartByCustomer(customer);

            if (cart == null) {
                LOG.info("No existing cart found. Creating a new one...");
                cart = new Order(new java.util.Date());
                cart.setCustomer(customer);
                cart.setStatus("CART");
                em.persist(cart); // Persist the new cart
            }

            // Check if product is already in the cart by comparing product IDs
            OrderLine existingLine = null;
            for (OrderLine ol : cart.getOrderLines()) {
                if (ol.getProduct().getId().equals(product.getId())) { // Compare by ID
                    existingLine = ol;
                    break;
                }
            }

            if (existingLine != null) {
                LOG.info("Product already in cart. Updating quantity...");
                if (product.getStock() < (existingLine.getQuantity() + quantity)) {
                    throw new InsufficientStockException("Cannot add more units of " + product.getName() + ". Only " + product.getStock() + " units available.");
                }
                existingLine.setQuantity(existingLine.getQuantity() + quantity);
                em.merge(existingLine); // Update existing line
            } else {
                LOG.info("Product not in cart. Adding new order line...");
                if (product.getStock() < quantity) {
                    throw new InsufficientStockException("Cannot add " + quantity + " units of " + product.getName() + ". Only " + product.getStock() + " units available.");
                }
                OrderLine orderLine = new OrderLine(product, quantity);
                orderLine.setOrder(cart);
                cart.getOrderLines().add(orderLine);
                em.persist(orderLine); // Persist new order line
            }

            // Merge the updated cart to ensure changes are persisted
            em.merge(cart);
            LOG.info("Product added to cart successfully.");
        } catch (Exception e) {
            LOG.severe("Error adding product to cart: " + e.getMessage());
            throw e; // Re-throw the exception after logging
        }
    }
    

    @Transactional
    public void checkout(Order cart) throws InsufficientStockException {
        LOG.info("Checkout for Order ID: " + cart.getId());
        for (OrderLine line : cart.getOrderLines()) {
            Product product = line.getProduct();
            if (product.getStock() < line.getQuantity()) {
                throw new InsufficientStockException("Product " + product.getName() + " has insufficient stock.");
            }
            product.setStock(product.getStock() - line.getQuantity());
            em.merge(product);
        }
        cart.setStatus("COMPLETED");
        em.merge(cart);
    }

    @Transactional
    public void updateCartItem(OrderLine orderLine, int newQuantity) throws InsufficientStockException {
        LOG.info("Updating OrderLine ID: " + orderLine.getId() + " to new quantity: " + newQuantity);
        if (newQuantity <= 0) {
            removeCartItem(orderLine);
        } else {
            Product product = orderLine.getProduct();
            if (product.getStock() < newQuantity) {
                throw new InsufficientStockException("Product " + product.getName() + " has insufficient stock.");
            }
            orderLine.setQuantity(newQuantity);
            em.merge(orderLine);
        }
    }

    @Transactional
    public void removeCartItem(OrderLine orderLine) {
        LOG.info("Removing OrderLine ID: " + orderLine.getId());
        // Remove the orderLine from the order's list to maintain consistency
        Order order = orderLine.getOrder();
        if (order != null) {
            order.getOrderLines().remove(orderLine);
            em.merge(order); // Merge the updated order
        }
        em.remove(em.contains(orderLine) ? orderLine : em.merge(orderLine));
    }
}
