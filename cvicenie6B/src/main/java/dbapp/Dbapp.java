/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbapp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Faktura;
import entities.Polozka;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author vsa
 */
public class Dbapp {

    public static void main(String... args) {
        pocetFaktur("Z1");
    }

    static int pocetFaktur(String zakaznik) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbappPU");
        EntityManager em = emf.createEntityManager();
        final List resultList = em.createNamedQuery("Faktura.findByZakaznik")
                .setParameter("zakaznik", zakaznik).getResultList();
        return resultList.size();
    }

    static void pridajPolozku(int id, String produkt, double cena) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbappPU");
        EntityManager em = emf.createEntityManager();
        Faktura faktura = new Faktura();
        try {
            faktura = (Faktura) em.createNamedQuery("Faktura.findById")
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException __) {
            em.getTransaction().rollback();
        }
        final Polozka polozka = new Polozka();
        em.getTransaction().begin();
        polozka.setProdukt(produkt);
        polozka.setCena(cena);
        faktura.getPolozky().add(polozka);
        faktura.setAktualizacia(new Date());
        em.persist(faktura);
//        em.getTransaction().commit();
    }
}
