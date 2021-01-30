/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("skuska")
@Singleton
public class SkuskaResource {

    @Context
    private UriInfo context;

    private Map<String, Skuska> skusky;

    /**
     * Creates a new instance of SkuskaResource
     */
    public SkuskaResource() {
        this.skusky = new HashMap<>();
        Skuska s = new Skuska();
        s.setPredmet("TZI");
        s.setDen("piatok");
        s.setStudent(new ArrayList<>());
        this.skusky.put("TZI", s);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public String createSkuska(Skuska skuska) {
        if (Objects.isNull(skuska)) {
            return null;
        }
        if (this.skusky.containsKey(skuska.getPredmet())) {
            return "NIC";
        } else {
            this.skusky.put(skuska.getPredmet(), skuska);
            return skuska.getPredmet();
        }
    }

    @GET
    @Path("{predmet}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getStudentsInSkuska(@PathParam(value = "predmet") String predmet) {
        if (Objects.isNull(predmet)) {
            return "0";
        }
        Skuska s = this.skusky.get(predmet);
        if (Objects.isNull(s)) {
            return "0";
        } else {
            return "" + s.getStudent().size();
        }
    }

    @GET
    @Path("{predmet}")
    @Produces(MediaType.APPLICATION_XML)
    public Skuska getSkuska(@PathParam(value = "predmet") String predmet) {
        if (Objects.isNull(predmet)) {
            return null;
        }
        Skuska s = this.skusky.get(predmet);
        if (Objects.isNull(s)) {
            return null;
        } else {
            return s;
        }
    }

    @POST
    @Path("{predmet}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void enrollStudent(@PathParam(value = "predmet") String predmet, String student) {
        if (Objects.isNull(predmet) || Objects.isNull(student)) {
            return;
        }
        Skuska s = this.skusky.get(predmet);
        if (Objects.isNull(s)) {
            return;
        }
        s.getStudent().add(student);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getSkusky(@QueryParam("student") String student) {
        if (Objects.isNull(student)) {
            return "";
        }
        List<String> p = new ArrayList<>();
        for (Skuska s : skusky.values()) {
            if (s.getStudent().contains(student)) {
               p.add(s.getPredmet());
            }
        }
        return String.join(" ", p);        
    }

    /**
     * Retrieves representation of an instance of rest.SkuskaResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of SkuskaResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
