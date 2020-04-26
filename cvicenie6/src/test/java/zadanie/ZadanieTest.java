/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zadanie;

import edu.iko.zadaniemv.Akcia;
import edu.iko.zadaniemv.Prednaska;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZadanieTest {

    @BeforeClass
    public static void setUpClass() throws ClassNotFoundException {
//        System.out.println("upclass");
        dropTables();
    }

    @Test
    public void test21() {
        int r = Zadanie.pocet("A2");
        assertTrue("#A2 " + r, r==0);
    }
    @Test
    public void test22() {
        int r = Zadanie.pocet("L2");
        assertTrue("#L2 " + r, r==1);
    }
    
    
    @Test
    public void test31() {
        assertTrue("C1 A1", Zadanie.maprednasku("C1", "A1"));
    }
    
    @Test
    public void test32() {
        assertTrue("C1 A2", Zadanie.maprednasku("C1", "A2"));
    }
    
    @Test
    public void test33() {
        assertFalse("NOT C1 A3", Zadanie.maprednasku("C1","A3"));
    }
    
    @Test
    public void test34() {
        assertFalse("NOT C2 A1", Zadanie.maprednasku("C2","A1"));
    }
    
    @Test
    public void test35() {
        assertFalse("NOT C3 A1", Zadanie.maprednasku("C3","A1"));
    }
    
    @Test
    public void test1Create() {
//        System.out.println("create");
        create();
    }

    public void create() {
        Akcia c1 = new Akcia();
        c1.setId(1L);
        c1.setNazov("C1");
        c1.setTermin(new Date());
        c1.setPrednasky(new ArrayList<>());

        Akcia c2 = new Akcia();
        c2.setId(2L);
        c2.setNazov("C2");
        c2.setTermin(new Date());
        c2.setPrednasky(new ArrayList<>());

        Prednaska l1 = new Prednaska();
        l1.setId(11L);
        l1.setNazov("L1");
        l1.setAutori(new ArrayList<>());

        Prednaska l2 = new Prednaska();
        l2.setId(12L);
        l2.setNazov("L2");
        l2.setAutori(new ArrayList<>());

        c1.getPrednasky().add(l1);
        c1.getPrednasky().add(l2);
        l1.setKonferencia(c1);
        l2.setKonferencia(c1);

        l1.getAutori().add("A1");
        l1.getAutori().add("A2");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("zadaniePU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(c1);
            em.persist(c2);
            em.persist(l1);
            em.persist(l2);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    static private void dropTables() throws ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");

        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            Statement st = con.createStatement();
            st.executeUpdate("DROP TABLE PREDNASKA_AUTORI");

        } catch (SQLException ex) {
            Logger.getLogger(ZadanieTest.class.getName()).log(Level.INFO, ex.getMessage());
        }
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            Statement st = con.createStatement();
            st.executeUpdate("DROP TABLE AKCIA_PREDNASKA");

        } catch (SQLException ex) {
            Logger.getLogger(ZadanieTest.class.getName()).log(Level.INFO, ex.getMessage());
        }
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            Statement st = con.createStatement();
            st.executeUpdate("DROP TABLE PREDNASKA_AKCIA");

        } catch (SQLException ex) {
            Logger.getLogger(ZadanieTest.class.getName()).log(Level.INFO, ex.getMessage());
        }
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            Statement st = con.createStatement();
            st.executeUpdate("DROP TABLE PREDNASKA");

        } catch (SQLException ex) {
            Logger.getLogger(ZadanieTest.class.getName()).log(Level.INFO, ex.getMessage());
        }
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            Statement st = con.createStatement();
            st.executeUpdate("DROP TABLE AKCIA");

        } catch (SQLException ex) {
            Logger.getLogger(ZadanieTest.class.getName()).log(Level.INFO, ex.getMessage());
        }
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            Statement st = con.createStatement();
            st.executeUpdate("DROP TABLE SEQUENCE");

        } catch (SQLException ex) {
            Logger.getLogger(ZadanieTest.class.getName()).log(Level.INFO, ex.getMessage());
        }
    }

}
