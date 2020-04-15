/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omos.microsystems.customerapp.rest;

import omos.microsystems.customerapp.entities.Customers;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author omozegieaziegbe
 */
@ApplicationScoped
public class CustomerService {

    @PersistenceContext(unitName = "customerAppPU")
    EntityManager em;

    public List getAll() {
        return em.createNamedQuery("Customers.findAll", Customers.class).getResultList();
    }

    public Customers findById(Integer id) {
        return em.find(Customers.class, id);
    }

    @Transactional
    public void update(Customers customer) {
        em.merge(customer);
    }

    @Transactional
    public void create(Customers customer) {
        em.persist(customer);
    }

    @Transactional
    public void delete(Customers customer) {
        if (!em.contains(customer)) {
            customer = em.merge(customer);
        }
        em.remove(customer);
    }
}
