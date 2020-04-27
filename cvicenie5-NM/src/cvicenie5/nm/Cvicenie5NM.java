/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvicenie5.nm;

import cvicenie5.nm.entities.Autor;
import cvicenie5.nm.entities.Kniha;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author vsa
 */
public class Cvicenie5NM {

    public static void create() {
        Autor o1 = new Autor();
        o1.setMeno("Hopcroft");

        Autor o2 = new Autor();
        o2.setMeno("Ullman");

        Autor o3 = new Autor();
        o3.setMeno("Aho");

        Kniha k1 = new Kniha();
        k1.setId(333L);
        k1.setNazov("Uvod do teorie automatov");

        Kniha k2 = new Kniha();
        k2.setNazov("Algoritmy a datove struktury");

        o1.setDielo(new ArrayList<>());
        o1.getDielo().add(k1);
        o1.getDielo().add(k2);

        o2.setDielo(new ArrayList<>());
        o2.getDielo().add(k1);
        o2.getDielo().add(k2);

        o3.setDielo(new ArrayList<>());
        o3.getDielo().add(k2);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cvicenie5-NMPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {

            em.persist(k1);
            em.persist(k2);
            em.persist(o1);
            em.persist(o2);
            em.persist(o3);

            em.getTransaction().commit();
            em.clear();

          

        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }

    public static void read() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cvicenie5-NMPU");
        EntityManager em = emf.createEntityManager();
        Kniha k = em.find(Kniha.class, 333L);
        System.out.println(k.getNazov());
        System.out.println(k.getAutor().size());
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    // najskor pustit samostatne create
//               create();
// az potom pustit samostatne read
               read();
        
     
    }
    

    
}
