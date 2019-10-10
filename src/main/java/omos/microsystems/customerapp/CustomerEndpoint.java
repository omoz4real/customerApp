/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omos.microsystems.customerapp;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author omozegieaziegbe
 */
@RequestScoped
@Path("customers")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class CustomerEndpoint {

    @Inject
    CustomerService customerService;

    @GET
    public Response getAll() {
        return Response.ok(customerService.getAll()).build();
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") Integer id) {
        Customers customer = customerService.findById(id);
        return Response.ok(customer).build();
    }

    @POST
    public Response create(Customers customer) throws Exception {
        customerService.create(customer);
        URI location = new URI("http://localhost:8080//customerApp-1.0/webresources/customers/" + customer.getId());
        return Response.created(location).build();
//        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Integer id, Customers customer) {
        Customers updateCustomer = customerService.findById(id);
        updateCustomer.setFullname(customer.getFullname());
        updateCustomer.setAddress(customer.getAddress());
        updateCustomer.setCourse(customer.getCourse());
        updateCustomer.setCity(customer.getCity());
        updateCustomer.setEmail(customer.getEmail());
        customerService.update(updateCustomer);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        Customers getCustomer = customerService.findById(id);
        customerService.delete(getCustomer);
        return Response.ok().build();
    }
    
    

}
