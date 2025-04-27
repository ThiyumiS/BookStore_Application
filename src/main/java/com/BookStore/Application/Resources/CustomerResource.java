package com.BookStore.Application.Resources;

import com.BookStore.Application.Model.Customer;
import com.BookStore.Application.Services.CustomerService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private CustomerService customerService = new CustomerService();

    @GET
    public Response getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return Response.ok(customers).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = customerService.getCustomerById(id);
        return Response.ok(customer).build();
    }

    @POST
    public Response createCustomer(Customer customer) {
        if (!isValidCustomer(customer)) {
            throw new com.BookStore.Application.Exceptions.InvalidInputException("Invalid customer data provided.");
        }
        Customer createdCustomer = customerService.createCustomer(customer);
        return Response.status(Response.Status.CREATED)
                .entity(createdCustomer)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer customer) {
        if (!isValidCustomer(customer)) {
            throw new com.BookStore.Application.Exceptions.InvalidInputException("Invalid customer data provided.");
        }
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return Response.ok(updatedCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        customerService.deleteCustomer(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private boolean isValidCustomer(Customer customer) {
        return customer.getFirstName() != null && !customer.getFirstName().trim().isEmpty() &&
                customer.getLastName() != null && !customer.getLastName().trim().isEmpty() &&
                customer.getEmail() != null && !customer.getEmail().trim().isEmpty();
    }
}
