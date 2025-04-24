package com.BookStore.Application.Resources;

import com.BookStore.Application.Exceptions.CustomerNotFoundException;
import com.BookStore.Application.Model.Customer;
import com.BookStore.Application.Services.CustomerService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customers")
public class CustomerResource {
    
    @Inject
    private CustomerService customerService;
    
    /**
     * Get all customers
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return Response.ok(customers).build();
    }
    
    /**
     * Get a specific customer by ID
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = customerService.getCustomerById(id);
        
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer with ID " + id + " not found")
                    .build();
        }
        
        return Response.ok(customer).build();
    }
    
    /**
     * Create a new customer
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        // Validate input
        if (customer == null || !isValidCustomer(customer)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid customer data provided")
                    .build();
        }
        
        Customer createdCustomer = customerService.createCustomer(customer);
        return Response.status(Response.Status.CREATED)
                .entity(createdCustomer)
                .build();
    }
    
    /**
     * Update an existing customer
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") int id, Customer customer) {
        // Validate input
        if (customer == null || !isValidCustomer(customer)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid customer data provided")
                    .build();
        }
        
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        
        if (updatedCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer with ID " + id + " not found")
                    .build();
        }
        
        return Response.ok(updatedCustomer).build();
    }
    
    /**
     * Delete a customer by ID
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        boolean deleted;
        try {
            customerService.deleteCustomer(id);
            deleted = true; // If no exception is thrown, the customer was deleted successfully
        } catch (CustomerNotFoundException e) {
            deleted = false; // If an exception is thrown, the customer was not found
        }

        //boolean deleted = customerService.deleteCustomer(id);
        
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer with ID " + id + " not found")
                    .build();
        }
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    /**
     * Validate customer object has required fields
     */
    private boolean  isValidCustomer(Customer customer) {
        return customer.getFirstName() != null && !customer.getFirstName().trim().isEmpty() &&
               customer.getLastName() != null && !customer.getLastName().trim().isEmpty() &&
               customer.getEmail() != null && !customer.getEmail().trim().isEmpty();
    }
}
