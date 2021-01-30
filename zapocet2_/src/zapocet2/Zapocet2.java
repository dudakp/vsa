package zapocet2;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class Zapocet2 {

    /**
     * @param args the command line arguments
     *
     * Len pre vase testovanie. Mozete si upravit.
     */
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("zapocet2PU");
        EntityManager em = emf.createEntityManager();

        pridajVyucujuceho(em, "Hrach", "OOP");
        pridajVyucujuceho(em, "Mrkva", "VSA");
        pridajVyucujuceho(em, "Mrkva", "ASOS");
        pridajVyucujuceho(em, "Mrkva", "ASOS");

        System.out.println("Mrkvov uvazok: " + pocetPredmetov(em, "Mrkva"));    // vypise 2  

        Set<Osoba> z1 = vyucujuci(em, "VSA");
        System.out.println("Pocet vyucujucich VSA: " + z1.size());              // vypise 1
    }

    /* Vrati zoznam vyucujucich predmetu so zadanym kodom
     * Ak kod nie je zadany alebo predmet s danym kodom neexistuje vrati null.
     */
    public static Set<Osoba> vyucujuci(EntityManager em, String kodPredmetu) throws Exception {
        final Predmet predmet;
        try {
            predmet = (Predmet) em.createNamedQuery("Predmet.findByKod")
                    .setParameter("kod", kodPredmetu)
                    .getSingleResult();
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return predmet.getVyucujuci();
    }

    /* Vrati pocet predmetov, ktore vyucuje osoba so zadanym menom. 
     * Ak meno nie je zadane allepebo osoba s danym menom neexistuje vrati 0.
     * Pozn. Metoda sa moze spolahnut na to, ze v DB je meno osoby jedinecne
     */
    public static int pocetPredmetov(EntityManager em, String meno) throws Exception {
        if (Objects.isNull(meno)) {
            return 0;
        }
        int pocet;
        Osoba osoba;
        try {
            osoba = (Osoba) em.createNamedQuery("Osoba.findByMeno")
                    .setParameter("meno", meno)
                    .getSingleResult();
            if (Objects.isNull(osoba.getPredmety())) {
                return 0;
            } else {
                pocet = osoba.getPredmety().size();
            }

        } catch (NoResultException e) {
            return 0;
        }
        return pocet;
    }

    /* Prida predmetu vyucujuceho:
     *
     * @param em            entity manager
     * @param meno          meno vyucujuceho
     * @param kodPredmetu   kod predmetu
     *
     * Metoda vyhlada osobu podla mena a predmet podla kodu. 
     *   Pozn. Metoda sa moze spolahnut na to, ze v DB je meno osoby jedinecne
     * Ak osoba s danym menom v DB neexistuje vytvori ju, pricom 
     *   datum narodenia nebude zadany (ostane null) 
     * Ak predmet s danym kodom neexistuje, vytvori ho, pricom rocnik bude "BC-1". 
     * Nasledne zaradi osobu medzi vyucujucich predmetu. 
     *
     * Navratova hodnota: 
     *   false: ak niektory z argumentov funkcie bol null. 
     *   true:  inak. 
     */
    public static boolean pridajVyucujuceho(EntityManager em, String meno, String kodPredmetu) throws Exception {
        if (Objects.isNull(em) || Objects.isNull(meno) || Objects.isNull(kodPredmetu)) {
            return false;
        }
        
        Osoba vyucujuci;
        Predmet predmet;
        em.getTransaction().begin();
        try {
        vyucujuci = (Osoba) em.createNamedQuery("Osoba.findByMeno")
                .setParameter("meno", meno)
                .getSingleResult();
        } catch (NoResultException e) {
            vyucujuci = new Osoba();
            vyucujuci.setMeno(meno);
//            em.persist(vyucujuci);
//            throw e;
        }
        try {
            predmet = (Predmet) em.createNamedQuery("Predmet.findByKod")
                    .setParameter("kod", kodPredmetu)
                    .getSingleResult();
            
            if (Objects.isNull(predmet.getVyucujuci())) {
                predmet.setVyucujuci(new HashSet<>());
            }
            
            if (Objects.isNull(vyucujuci.getPredmety())) {
                vyucujuci.setPredmety(new HashSet<>());
            }
            
            predmet.getVyucujuci().add(vyucujuci);
            vyucujuci.getPredmety().add(predmet);
            em.persist(predmet);
            em.persist(vyucujuci);

        } catch (NoResultException e) {
            predmet = new Predmet();
            predmet.setKod(kodPredmetu);
            predmet.setRocnik("BC-1");
            
            predmet.setVyucujuci(new HashSet<>());
            predmet.getVyucujuci().add(vyucujuci);
            
//            vyucujuci.setPredmety(new HashSet<>());
            if (Objects.isNull(vyucujuci.getPredmety())) {
                vyucujuci.setPredmety(new HashSet<>());
            }
            
            vyucujuci.getPredmety().add(predmet);
            em.persist(predmet);
            em.persist(vyucujuci);
        }        
        em.getTransaction().commit();
        return true;        
    }

}
