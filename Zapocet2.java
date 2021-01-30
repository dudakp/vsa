package zapocet2;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
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
//        persist(new Osoba());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("zapocet2PU");
        EntityManager em = emf.createEntityManager();

        novyPredmet(em, "OOP", "Hrach" );
        novyPredmet(em, "VSA", "Mrkva");
        novyPredmet(em, "ASOS", "Mrkva");

        System.out.println("Mrkvov uvazok: " + pocetPrednasok(em, "Mrkva"));    // vypise 2  

        Osoba vyuc = prednasajuci(em, "VSA");
        System.out.println("Prednasajuci VSA: " + vyuc);              
    }

    /* Vrati profesora prednasajuceho predmet s danym kodom
     * Ak kod nie je zadany alebo predmet s danym kodom neexistuje vrati null.
     */
    public static Osoba prednasajuci(EntityManager em, String kodPredmetu) throws Exception {
        if (isNull(em)) return null;
        try {
            
            if (isNull(kodPredmetu)) return null;
            Predmet p = em.createNamedQuery("Predmet.byCode", Predmet.class)
                    .setParameter("kod", kodPredmetu)
                    .getSingleResult();
            
            if (isNull(p.getProfesor())) {
                return null;
            } else {
                return p.getProfesor();
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    /* Vrati pocet predmetov, ktore prednasa osoba so zadanym menom. 
     * Ak meno nie je zadane alebo osoba s danym menom neexistuje vrati 0.
     * Pozn. Metoda sa moze spolahnut na to, ze v DB je meno osoby jedinecne
     */
    public static int pocetPrednasok(EntityManager em, String meno) throws Exception {
        if (isNull(em)) return 0;
        try {
            if (isNull(meno)) return 0;
            return em.createNamedQuery("Predmet.count", Long.class)
                    .setParameter("meno", meno)
                    .getSingleResult().intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    /* Vytvori novy predmet.
     *
     * @param em            entity manager
     * @param kodPredmetu   kod predmetu
     * @param meno          meno profesora ktory predmet prednasa
     *
     * Metoda naprv zisti ci predmet s danym kodom uz neexistuje.
     *   ak existuje, nerobi nic viac ale vrati false.
     *
     * Ak predmet s danym kodom neexistuje, vytvori ho, pricom rocnik bude "BC-1".
     * Nasledne, ak je meno prednasajuceho zadane, vyhlada osobu s danym menom. 
     *   Ak osoba v DB neexistuje vytvori ju, pricom datum narodenia nebude zadany (ostane null) 
     * Nakoniec nastavi tuto osobu ako profesora, ktory prednasa novy predmet.
     *   Pozn. ak meno nebolo zadane, prednasajuci profesor ostane null.   
     *   Pozn. metoda sa moze spolahnut na to, ze v DB je meno osoby jedinecne.
     *
     * Navratova hodnota: 
     *   false: ak predmet uz existoval alebo kod predmetu nebol zadany. 
     *   true:  inak. 
     */
    public static boolean novyPredmet(EntityManager em, String kodPredmetu, String meno) throws Exception {
        if (isNull(em)) return false;
        if (isNull(kodPredmetu)) return false;
        try {
            Predmet p = em.createNamedQuery("Predmet.byCode", Predmet.class)
                    .setParameter("kod", kodPredmetu)
                    .getSingleResult();
            
            return false;
        } catch (NoResultException e) {
            em.getTransaction().begin();
            Predmet p = new Predmet();
            p.setKod(kodPredmetu);
            p.setRocnik("BC-1");
            
            
            if (!isNull(meno)) {
                try {
                    Osoba vyucujuci = em.createNamedQuery("Osoba.byMeno", Osoba.class)
                            .setParameter("meno", meno)
                            .getSingleResult();
                    
                    if (isNull(vyucujuci.getPrednasky())) {
                        List<Predmet> prednasky = new ArrayList<>();
                        prednasky.add(p);
                        vyucujuci.setPrednasky(prednasky);
                        p.setProfesor(vyucujuci);
                    } else {
                        List<Predmet> prednasky = vyucujuci.getPrednasky();
                        prednasky.add(p);
                        vyucujuci.setPrednasky(prednasky);
                        p.setProfesor(vyucujuci);
                    }
                    em.persist(vyucujuci);
                    em.persist(p);
                } catch (NoResultException r) {
                    Osoba vyucujuci = new Osoba();
                    vyucujuci.setPrednasky(new ArrayList<>());
                    vyucujuci.setMeno(meno);
                    vyucujuci.getPrednasky().add(p);
                    p.setProfesor(vyucujuci);
                    em.persist(vyucujuci);
                    em.persist(p);
                }
                em.persist(p);
                       
            }
            em.getTransaction().commit();
            return true;
        }
    }

}
