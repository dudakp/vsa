/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testClient;

import javax.ws.rs.WebApplicationException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author igor
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SkuskaKlientTest {

    static private Server jettyServer;

    static private String root;

    static private String n1;
    static private String n2;
    static private String n3;
    static private String n4;
    static private String n5;

    public SkuskaKlientTest() {
    }

    @BeforeClass
    static public void setUp() {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/RestUnitTest/resources");

        jettyServer = new Server(9999);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "rest");

        try {
            jettyServer.start();
//            System.out.println("Jetty started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    static public void tearDown() {
        try {
//            System.out.println("Jetty stopping...");
            jettyServer.stop();
//       jettyServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // zisti root resource
    @Test
    public void UT00() {
        String emsg = "";
        root = "skuska";
        SkuskaClient client = new SkuskaClient(root);
        try {
            client.pocet(null, null);
            return;
        } catch (WebApplicationException e) {
            emsg =  e.getMessage();            
        }

        root = "skuska";
        client = new SkuskaClient(root);
        try {
            client.getText("VSA", "nikto");
            return;
        } catch (WebApplicationException e) {
        }

        root = "generic";
        client = new SkuskaClient(root);
        try {
            client.pocet(null, null);
            fail("POZOR ROOT: generic");
        } catch (WebApplicationException e) {
            root = "skuska";
            fail("POZOR ROOT: neznamy " + emsg);
        }

    }

    // 2b
    // NEMA
    @Test
    public void UT01_get_VSAnikto() {
        SkuskaClient client = new SkuskaClient(root);
        String r= "";
        try {
            r = client.getText("VSA", "nikto");
            client.close();
        } catch (WebApplicationException e) {
            //System.out.println("neocakavana chyba: napr zle osetrene hranice ");
            fail("CHYBA WEB:" + e.getMessage());
        }
        assertEquals("NEMA", r.trim());
    }

    // 2b
    // NEPLATNY PREDMET alebo HTTP 404 alebo 204
    @Test
    public void UT02_get_XXXnikto() {
        SkuskaClient client = new SkuskaClient(root);
        String r= "";
        try {
            r = client.getText("XXX", "nikto");
            client.close();
        } catch (WebApplicationException e) {
            if (!e.getMessage().contains("HTTP 40") && !e.getMessage().contains("HTTP 20")) {
                fail("CHYBA! mal prist HTTP 404: " + e.getMessage());
            }
        }
        assertTrue("CHYBA! mal prist NEPLATNY PREDMET but was> " + r, r.trim().contains("NEPLAT"));
        //assertEquals("NEPLATNY PREDMET", r.trim());
    }

    
    //1b
    @Test
    public void UT04_put_VSAs1() {
        SkuskaClient client = new SkuskaClient(root);
        try {
            client.putText("vsa-s1","VSA", "s1");
            client.close();
        } catch (WebApplicationException e) {
            //neocakavana chyba");
            fail("CHYBA WEB:" + e.getMessage());
        }
    }
    
    //1b
    @Test
    public void UT05_put_ostatne() {
        SkuskaClient client = new SkuskaClient(root);
        try {
            client.putText("aza-s1","AZA", "s1");
            client.putText("aza-s2","AZA", "s2");
            client.putText("swi-s1","SWI", "s1");
            client.putText("swi-s2","SWI", "s2");
            client.putText("swi-s3","SWI", "s3");
            client.close();
        } catch (WebApplicationException e) {
            //neocakavana chyba");
            fail("CHYBA WEB:" + e.getMessage());
        }
    }
    
    //1b
    @Test
    public void UT06_get_VSAs1() {
        SkuskaClient client = new SkuskaClient(root);
        String r= "";
        try {
            r = client.getText("VSA", "s1");
            client.close();
        } catch (WebApplicationException e) {
            fail("CHYBA WEB:" + e.getMessage());
        }
        assertEquals("vsa-s1", r.trim());
    }
    
    // 2b
    @Test
    public void UT07_get_ostatne() {
        SkuskaClient client = new SkuskaClient(root);
        String r= "";
        try {
            r = client.getText("AZA", "s1");
            assertEquals("aza-s1", r.trim());
            r = client.getText("AZA", "s2");
            assertEquals("aza-s2", r.trim());
            r = client.getText("SWI", "s1");
            assertEquals("swi-s1", r.trim());
            r = client.getText("SWI", "s2");
            assertEquals("swi-s2", r.trim());
            r = client.getText("SWI", "s3");
            assertEquals("swi-s3", r.trim());
            client.close();
        } catch (WebApplicationException e) {
            fail("CHYBA WEB:" + e.getMessage());
        }
    }
    
    //POCET 
    // 1b
    @Test
    public void UT08_pocet_neplatny_predmet() {
        SkuskaClient client = new SkuskaClient(root);
        String r= "";
        try {
            r = client.pocet(null, "XXX");
            client.close();
        } catch (WebApplicationException e) {
            fail("CHYBA WEB:" + e.getMessage());
        }
        assertTrue("CHYBA! mal prist NEPLATNY PREDMET but was> " + r, r.trim().contains("NEPLAT"));
        //assertEquals("NEPLATNY PREDMET", r.trim());
    }
    
    // 1b 
    @Test
    public void UT09_pocet_null_null() {
        SkuskaClient client = new SkuskaClient(root);
        String r= "";
        try {
            r = client.pocet(null, null);
            client.close();
        } catch (WebApplicationException e) {
            fail("CHYBA WEB:" + e.getMessage());
        }
        assertEquals("CHYBA", r.trim());
    }

    // 1b
    @Test
    public void UT10_pocet_VSA() {
        SkuskaClient client = new SkuskaClient(root);
        String r= "";
        try {
            r = client.pocet(null, "VSA");
            client.close();
        } catch (WebApplicationException e) {
            fail("CHYBA WEB:" + e.getMessage());
        }
        assertEquals("1", r.trim());
    }
    
    // musi dopadnut rovnako ako UT10
    @Test
    public void UT11_pocet_VSAs2() {
        SkuskaClient client = new SkuskaClient(root);
        String r= "";
        try {
            r = client.pocet("s2", "VSA");
            client.close();
        } catch (WebApplicationException e) {
            fail("CHYBA WEB:" + e.getMessage());
        }
        assertEquals("1", r.trim());
    }
    
    // 1b
    @Test
    public void UT12_pocet_SWI() {
        SkuskaClient client = new SkuskaClient(root);
        String r= "";
        try {
            r = client.pocet(null, "SWI");
            client.close();
        } catch (WebApplicationException e) {
            fail("CHYBA WEB:" + e.getMessage());
        }
        assertEquals("3", r.trim());
    }

    // 2b + 2 premiove
    @Test
    public void UT13_pocet_predmetov() {
        SkuskaClient client = new SkuskaClient(root);
        String r;
        try {
            r = client.pocet("s1", null);
            assertEquals("3", r.trim());
            r = client.pocet("s2", null);
            assertEquals("2", r.trim());
            r = client.pocet("s3", null);
            assertEquals("1", r.trim());
            client.close();
        } catch (WebApplicationException e) {
            fail("CHYBA WEB:" + e.getMessage());
        }
    }
    
    // 2b
    @Test
    public void UT14_put_zmena() {
        SkuskaClient client = new SkuskaClient(root);
        try {
            client.putText("zmena","SWI", "s3");
            String r = client.getText("SWI", "s3");
            assertEquals("zmena", r.trim());
            client.close();
        } catch (WebApplicationException e) {
            //neocakavana chyba");
            fail("CHYBA WEB:" + e.getMessage());
        }
    }

    // 2b
    @Test
    public void UT15_remove() {
        SkuskaClient client = new SkuskaClient(root);
        try {
            client.delete("AZA", "s2");
            String r = client.getText("AZA", "s2");
            assertEquals("NEMA", r.trim());
            client.close();
        } catch (WebApplicationException e) {
            //neocakavana chyba");
            fail("CHYBA WEB:" + e.getMessage());
        }
    }

    // 1b
    // nema nic robit
    @Test
    public void UT16_remove_nikto() {
        SkuskaClient client = new SkuskaClient(root);
        try {
            client.delete("VSA", "nikto");
            // nesmie nic zmazat
            String r = client.getText("VSA", "s1");
            assertEquals("vsa-s1", r.trim());
            client.close();
        } catch (WebApplicationException e) {
            //neocakavana chyba");
            fail("CHYBA WEB:" + e.getMessage());
        }
    }
    
}
