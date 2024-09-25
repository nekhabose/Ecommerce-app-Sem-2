/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1;
import edu.iit.sat.itmd4515.nbose1.domain.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nekha
 */
public class ProductValidationTest {

    private static ValidatorFactory validatorFactory;
    private Validator validator;

    @BeforeAll
    public static void setupValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @BeforeEach
    public void setupValidator() {
        validator = validatorFactory.getValidator();
    }

    @Test
    public void sunnyDayProductValidation() {
        // Valid product data (sunny-day scenario)
        Product product = new Product("Laptop", new BigDecimal("999.99"), null, 50);

        
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        // Assert no violations (sunny-day: everything is valid)
        assertTrue(violations.isEmpty(), "There should be no constraint violations for a valid product.");
    }

    @Test
    public void rainyDayProductNameValidation() {
        // Invalid product data (rainy-day scenario: name is null)
        Product product = new Product(null, new BigDecimal("999.99"), null, 50);

        // Validate the product
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        // Assert violations (rainy-day: product name should not be null)
        assertFalse(violations.isEmpty(), "There should be constraint violations for a null product name.");

        // Check for a specific violation related to @NotNull on the name field
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name") &&
                v.getMessage().equals("must not be null")));
    }

    @Test
    public void rainyDayProductPriceValidation() {
        // Invalid product data (rainy-day scenario: negative price)
        Product product = new Product("Laptop", new BigDecimal("-10.00"), null, 50);

        // Validate the product
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        // Assert violations (rainy-day: price should not be negative)
        assertFalse(violations.isEmpty(), "There should be constraint violations for a negative product price.");

        // Check for a specific violation related to @DecimalMin on the price field
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price") &&
                v.getMessage().equals("must be greater than or equal to 0.0")));
    }

    @Test
    public void rainyDayProductStockValidation() {
        // Invalid product data (rainy-day scenario: stock is negative)
        Product product = new Product("Laptop", new BigDecimal("999.99"), null, -5);

        // Validate the product
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        // Assert violations (rainy-day: stock should not be negative)
        assertFalse(violations.isEmpty(), "There should be constraint violations for negative stock.");

        // Check for a specific violation related to @Min on the stock field
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("stock") &&
                v.getMessage().equals("must be greater than or equal to 0")));
    }

   
    @AfterAll
    public static void tearDownValidatorFactory() {
        validatorFactory.close();
    }
}