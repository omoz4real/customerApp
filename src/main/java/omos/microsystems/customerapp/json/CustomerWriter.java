/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omos.microsystems.customerapp.json;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import omos.microsystems.customerapp.entities.Customers;

/**
 *
 * @author omozegieaziegbe
 */
@Provider
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerWriter implements MessageBodyWriter<Customers> {

    @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Customers.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Customers t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        // As of JAX-RS 2.0, the method has been deprecated and the 
        // value returned by the method is ignored by a JAX-RS runtime. 
        // All MessageBodyWriter implementations are advised to return -1 from 
        // the method.

        return -1;
    }

    @Override
    public void writeTo(Customers t, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
        JsonGenerator gen = Json.createGenerator(out);
        gen.writeStartObject()
//                .write("id", t.getId())
                .write("fullname", t.getFullname())
                .write("address", t.getAddress())
                .write("city", t.getCity())
                .write("email", t.getEmail())
                .write("course", t.getCourse())
                .writeEnd();
        gen.flush();

    }

}
