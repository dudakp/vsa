/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zadanie;

import edu.iko.zadaniemv.Akcia;
import edu.iko.zadaniemv.Prednaska;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author vsa
 */
public class Zadanie {

    public static void main(String... args) {
        maprednasku("C1", "A1");

    }

    static int pocet(String nazov) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("zadaniePU");
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Prednaska.findByName").setParameter("nazov", nazov).getResultList().size();
    }

    static boolean maprednasku(String akcia, String autor) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("zadaniePU");
        EntityManager em = emf.createEntityManager();
        Akcia akciaResult;
        try {
            akciaResult = (Akcia) em.createNamedQuery("Akcia.findByName")
                    .setParameter("nazov", akcia)
                    .getSingleResult();
        } catch (NoResultException __) {            
            return false;
        }
        final List<Prednaska> prednasky = akciaResult.getPrednasky();
        for (Prednaska pred : prednasky) {
            if(pred.getAutori().contains(autor))
                return true;
        }
        return false;
    }

}
