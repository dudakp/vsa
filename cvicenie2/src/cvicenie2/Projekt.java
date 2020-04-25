/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvicenie2;

import cvicenie2.entities.Kniha;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author vsa
 */
public class Projekt {

    private EntityManager em;

    public Projekt() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cvicenie2PU");
        this.em = emf.createEntityManager();
    }

    public Boolean aktializujKnihu(String isbn, String nazov, Double cena) {
        Query query = this.em.createNamedQuery("Kniha.findByIsbn", Kniha.class);        
        query.setParameter("isbn", isbn);
        this.em.getTransaction().begin();
        try {
            final Kniha knihaById = (Kniha) query.getSingleResult();
            System.out.println("Kniha uz existuje: " + knihaById.getNazov());
            if (null != nazov) {
                if (!nazov.equals(knihaById.getNazov())) {
                    System.out.println("Nazvy knih sa nezhoduju!");
                    this.em.getTransaction().rollback();
                    return false;
                } else {
//                    byId.setNazov(nazov);
                    knihaById.setCena(cena);
                    this.em.persist(knihaById);
                    this.em.getTransaction().commit();
                }
            } else {
                knihaById.setCena(cena);
                this.em.persist(knihaById);
                this.em.getTransaction().commit();
            }
        } catch (NoResultException e) {
            System.out.println("kniha neexistuje, vytvaram novu...");
            final Kniha kniha = new Kniha();
            kniha.setIsbn(isbn);
            kniha.setNazov(nazov);
            kniha.setCena(cena);
            this.em.persist(kniha);
            this.em.getTransaction().commit();
        }
        return true;
    }

    public void aktualizujCeny(Map<String, Double> cennik) {
//        Query query = em.createNamedQuery("Kniha.findByIsbn", Kniha.class);
        cennik.entrySet().stream()
                .forEach(kniha -> {
//                    query.setParameter("isbn", kniha.getKey());
                    this.aktializujKnihu(kniha.getKey(), null, kniha.getValue());
                });
    }

}
