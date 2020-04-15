/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omos.microsystems.customerapp.client;

import omos.microsystems.customerapp.entities.Customers;
import omos.microsystems.customerapp.json.CustomerWriter;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import omos.microsystems.customerapp.rest.CustomerService;

/**
 *
 * @author omozegieaziegbe
 */
@Named
@RequestScoped
public class microfrontend {
    
    Client client;
    WebTarget target;
    
    @Inject
    CustomerBackingBean bean;
    
    Customers customers;
    
     @Inject
    CustomerService customerService;
     
     @Inject HttpServletRequest httpServletRequest;
    
    @PostConstruct
    public void init() {
        bean=new CustomerBackingBean();
        client = ClientBuilder.newClient();
        target = client
                .target("http://localhost:8080/api/customers/");
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }
    
    public void load(String id) {
        if (id == null || id.length() <= 0)
            customers = new Customers();
        else
           customers = customerService.findById(Integer.valueOf(id));
    }
        
    public Customers[] getAllCustomers() {
        return target
                .request()
                .get(Customers[].class);
    }
    
    public Customers getCustomer() {
        Customers m = target
                .path("{customers}")
                .resolveTemplate("customers", bean.getId())
                .request()
                .get(Customers.class);
        return m;
    }
    
     public Customers getCustomerJson() {
        Customers m = target
                .path("{customers}")
                .resolveTemplate("customers", bean.getId())
                .request(MediaType.APPLICATION_JSON)
                .get(Customers.class);
        return m;
    }

      
    public String addCustomers() {
        Customers m = new Customers();
//        m.setId(bean.getId());
        m.setFullname(bean.getFullname());
        m.setEmail(bean.getEmail());
        m.setAddress(bean.getAddress());
        m.setCity(bean.getCity());
        m.setCourse(bean.getCourse());
        target
                .register(CustomerWriter.class)
                .request()
                .post(Entity.entity(m, MediaType.APPLICATION_JSON));
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Customer Added Successfully");
        context.addMessage("", message);
        context.getExternalContext().getFlash().setKeepMessages(true);
        return "addcustomer?faces-redirect=true";
        
    }

    public String deleteCustomer() {
        target
                .path("{id}")
                .resolveTemplate("id", bean.getId())
                .request().delete();
                System.out.println( "This is the Value" + bean.getId() );
        return "microfrontEnd?faces-redirect=true";
    }

//     public String deleteTodo(Customers id)    {
//        customerService.delete(id);
//        return "microfrontEnd?faces-redirect=true";
//        // without redirect page would not update and item would still be visible
//    }
     
    public CustomerBackingBean getBean() {
        return bean;
    }

    public void setBean(CustomerBackingBean bean) {
        this.bean = bean;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public Customers getCustomers() {
        return customers;
    } 
    
}
 