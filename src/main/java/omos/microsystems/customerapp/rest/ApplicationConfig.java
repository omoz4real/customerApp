/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omos.microsystems.customerapp.rest;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

/**
 *
 * @author omozegieaziegbe
 */
@ApplicationPath("api")
//@LoginConfig(authMethod = "MP-JWT", realmName="MP-JWT")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }


    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(omos.microsystems.customerapp.filter.CorsFilter.class);
        resources.add(omos.microsystems.customerapp.json.CustomerWriter.class);
        resources.add(omos.microsystems.customerapp.rest.CustomerEndpoint.class);
    }
    
}
