/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.Jedlo;
import entities.Menu;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.derby.iapi.util.StringUtil;

/**
 *
 * @author vsa
 */
@Stateless
@Path("menu")
@Transactional
public class MenuFacadeREST extends AbstractFacade<Menu> {

    private static final Logger LOG = Logger.getLogger(MenuFacadeREST.class.getName());
    
    

    @PersistenceContext(unitName = "rest1PU")
    private EntityManager em;

    public MenuFacadeREST() {
        super(Menu.class);
    }
     
    
    @GET
    @Path("{den: [0-9]+}")
    @Produces(MediaType.TEXT_PLAIN)    
    public int getMealCountForDay(@PathParam(value = "den" ) int den) {
        LOG.log(Level.INFO, String.valueOf(den));
        return this.em.createNamedQuery("Menu.findByDay", Menu.class)
                .setParameter("den", den)
                .getSingleResult().getJedlo().size();
    }
    
    @GET
    @Path("{den: [0-9]+}")
    @Produces(MediaType.APPLICATION_XML)    
    public Menu getMenuForDay(@PathParam(value = "den" ) int den) {
        LOG.log(Level.INFO, String.valueOf(den));
        Menu m = this.em.createNamedQuery("Menu.findByDay", Menu.class)
                .setParameter("den", den)
                .getSingleResult();
        LOG.log(Level.INFO, m.toString());
        return m;
    }
    
    @GET
    @Path("{den: [0-9]+}/{n: [0-9]+}")
    @Produces(MediaType.TEXT_PLAIN)    
    public String getMealNameByNumber(@PathParam(value = "den" ) int den, @PathParam(value = "n" ) int n) {
        LOG.log(Level.INFO, String.valueOf(den));
        Menu m = this.em.createNamedQuery("Menu.findByDay", Menu.class)
                .setParameter("den", den)
                .getSingleResult();
        Jedlo j;
        try {
          j = m.getJedlo().get(n);   
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }        
        return j.getNazov(); 
    }
    
    @GET 
    @Path("{den: [0-9]+}/{n: [0-9]+}")
    @Produces(MediaType.APPLICATION_XML)    
    public Jedlo getObjectMealByNumber(@PathParam(value = "den" ) int den, @PathParam(value = "n" ) int n) {
        LOG.log(Level.INFO, String.valueOf(den));
        Menu m = this.em.createNamedQuery("Menu.findByDay", Menu.class)
                .setParameter("den", den)
                .getSingleResult();
        Jedlo j;
        try {
          j = m.getJedlo().get(n);   
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }        
        return j; 
    }
    
    @POST
    @Path("{den: [0-9]+}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_XML)
    public String createFood(@PathParam(value = "den" ) int den, Jedlo jedlo) {
      LOG.log(Level.INFO, jedlo.toString());
      Menu m = this.em.createNamedQuery("Menu.findByDay", Menu.class)
                .setParameter("den", den)
                .getSingleResult();
      m.getJedlo().add(jedlo);
      
      this.em.persist(m);
      return jedlo.toString();
    }
    
    @DELETE 
    @Path("{den: [0-9]+}/{n: [0-9]+}")
    @Produces(MediaType.APPLICATION_XML)    
    public String deleteShit(@PathParam(value = "den" ) int den, @PathParam(value = "n" ) int n) {        
        Menu m = this.em.createNamedQuery("Menu.findByDay", Menu.class)
                .setParameter("den", den)
                .getSingleResult();
        m.getJedlo().remove(n);
        this.em.persist(m);
        return "bitch!";
    }


    @POST    
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit() {
        Jedlo j1= new Jedlo();
        j1.setNazov("segedin");
        j1.setCena(123.0);
        
        Jedlo j2= new Jedlo();
        j2.setNazov("halusky");
        j2.setCena(321.0);
        
        List<Jedlo> jedla = new ArrayList<>();
        
        jedla.add(j1);
        jedla.add(j2);
        
        Menu m = new Menu();
        m.setDen(1);
        m.setJedlo(new ArrayList<>());
        m.getJedlo().addAll(jedla);

        super.create(m);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
