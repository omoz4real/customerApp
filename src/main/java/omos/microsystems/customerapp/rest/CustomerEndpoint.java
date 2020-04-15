/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omos.microsystems.customerapp.rest;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import omos.microsystems.customerapp.entities.Customers;

/**
 *
 * @author omozegieaziegbe
 */
@RequestScoped
@Path("customers")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//@DenyAll
public class CustomerEndpoint implements Serializable {

    @Inject
    CustomerService customerService;
    
    private List<Customers> customers;
    
    @Context 
    UriInfo uriInfo;
    
    @Inject @Push
    private PushContext push;

    @PostConstruct
    public void load() {
        customers = customerService.getAll();
    }

    public List<Customers> getCustomers() {
        return customers;
    }

    public void onNewCustomer(@Observes Customers newCustomer) {
        customers.add(0, newCustomer);
        push.send("updateNotifications");
    }

    
    //@RolesAllowed({"ADMIN","USER"})
    @GET
    public Response getAll() {   
        return Response.ok(customerService.getAll()).build();
    }

    //@RolesAllowed({"ADMIN"})
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response getCustomer(@PathParam("id") Integer id) {
        Customers customer = customerService.findById(id);
        URI uri = uriInfo.getAbsolutePathBuilder().path(customer.getId().toString()).build();
        URI url = UriBuilder.fromPath(uriInfo.getAbsolutePath().toString()).path("{id}").build(customer.getId());
        System.out.println(uri + " " + url);
        return Response.ok(customer).build();
    }

    //@RolesAllowed({"ADMIN","USER"})
    @POST
    public Response create(Customers customer) throws Exception {
        customerService.create(customer);
        URI location = new URI("http://localhost:8080/api/customers/" + customer.getId());
        return Response.created(location).build();
//        return Response.ok().build();
    }

    //@RolesAllowed({"ADMIN"})
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

    //@RolesAllowed({"ADMIN"})
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        Customers getCustomer = customerService.findById(id);
        customerService.delete(getCustomer);
        return Response.ok().build();
    }
    
}
