package com.BookStore.Application.Services;

import com.BookStore.Application.Model.Customer;
import com.BookStore.Application.Storage.Storage;
import com.BookStore.Application.Exceptions.CustomerNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerService {
    
    /**
     * Get all customers from storage
     */
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(Storage.getCustomers().values());
    }
    
    /**
     * Get a customer by their ID
     * @throws CustomerNotFoundException if customer not found
     */
    public Customer getCustomerById(int id) {
        Customer customer = Storage.getCustomers().get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }
        return customer;
    }
    
    /**
     * Create a new customer
     */
    public Customer createCustomer(Customer customer) {
        int id = Storage.generateCustomerId();
        customer.setCustomerId(id);
        Storage.getCustomers().put(id, customer);
        return customer;
    }
    
    /**
     * Update an existing customer
     * @throws CustomerNotFoundException if customer not found
     */
    public Customer updateCustomer(int id, Customer updatedCustomer) {
        if (!Storage.getCustomers().containsKey(id)) {
            throw new CustomerNotFoundException( "Cannot update customer. Customer not found with ID: " + id);
        }
        
        updatedCustomer.setCustomerId(id);
        Storage.getCustomers().put(id, updatedCustomer);
        return updatedCustomer;
    }
    
    /**
     * Delete a customer by ID
     * @throws CustomerNotFoundException if customer not found
     */
    public void deleteCustomer(int id) {
        if (!Storage.getCustomers().containsKey(id)) {
            throw new CustomerNotFoundException( "Cannot delete customer. Customer not found with ID: " + id);
        }
        
        Storage.getCustomers().remove(id);
    }
}
