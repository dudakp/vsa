/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author vsa
 */
@Singleton
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;
    
    private List<String> texts;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
        texts = new ArrayList<>();
    }

    /**
     * Retrieves representation of an instance of rest.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        return this.texts.toString();
    }
    
    @GET
    @Path("{index: [0-9]+}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getOnIndex(@PathParam(value = "index") int index) {
        return this.texts.get(index);
    }

    
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String modifyOnIndex(@QueryParam(value = "index") int index,
            @QueryParam("content") String content) {
        this.texts.set(index, content);
        return this.texts.get(index);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void putText(@QueryParam(value = "content") String content) {
        this.texts.add(content);
    }
    
    @DELETE
    public void deleteAll() {
        this.texts.clear();
    }
}
