/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dedicnost;

import dedicnost.entities.Cesta;
import dedicnost.entities.Obchod;
import dedicnost.entities.filesystem.Datafile;
import dedicnost.entities.filesystem.Folder;
import dedicnost.entities.prednaskaExample.GuiElement;
import dedicnost.entities.prednaskaExample.RectElement;
import dedicnost.entities.prednaskaExample.TextElement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author vsa
 */
public class Dedicnost {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dedicnostPU");
//        EntityManager em = emf.createEntityManager();
//        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dedicnostPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
          
        create(em, tx);
        createShop(em);
        createFilesystem(em);
        
        List<TextElement> e = queryAllTextElements(em);
        List<String> t = queryAllTextElementNames(em);
        List<Obchod> r = queryAllShops(em);
        List<String> n = queryAllGeoObjectNames(em);
        List<Folder> fs = queryFilesystem(em);
        System.out.println(fs);
    }

    public static void create(EntityManager em, EntityTransaction tx) {
        tx.begin();

        GuiElement ge = new GuiElement();
        ge.setName("A gui element");

        TextElement te = new TextElement();
        te.setName("A text element");
        te.setText("hello");
        te.setFontFamily("Times New Roman");
        te.setFontSize(16);

        RectElement re = new RectElement();
        re.setName("A rectangle element");
        re.setHeight(100);
        re.setWidth(200);

        em.persist(ge);
        em.persist(te);
        em.persist(re);

        tx.commit();
    }
    
    public static void createShop(EntityManager em) {
        em.getTransaction().begin();

        Obchod ivanic = new Obchod();
        ivanic.setNazov("Potraviny u Ivanica");
        ivanic.setObec("soblahov");
        ivanic.setUlica("hlavna");
        ivanic.setCislo("325");
        ivanic.setOtvorene("A N O");
        
        Cesta k = new Cesta();
        k.setNazov("kokot");
        k.setKategoria(1);

        em.persist(ivanic);
        em.persist(k);

        em.getTransaction().commit();
    }
    
    public static void createFilesystem(EntityManager em) {
        em.getTransaction().begin();
        
        Folder f = new Folder();
        f.setName("priecinok");        
        f.setChildNodes(new ArrayList<>());
        Datafile file = new Datafile();
        file.setName("subor");
        file.setMode(123);
        file.setData("KOKOT ANO DOBRE?");
        f.getChildNodes().add(file);
        
        em.persist(f);
        
        em.getTransaction().commit();

    }

    public static List<TextElement> queryAllTextElements(EntityManager em) {
        return em.createQuery("select e from TextElement e", TextElement.class).getResultList();
    }
    
    public static List<String> queryAllGeoObjectNames(EntityManager em) {
        return em.createQuery("select e.nazov from GeoObject e", String.class).getResultList();
    }
    
    public static List<String> queryAllTextElementNames(EntityManager em) {
        return em.createQuery("select e.text from TextElement e", String.class).getResultList();
    }        
    
    public static List<Obchod> queryAllShops(EntityManager em) {
        return em.createQuery("select e from Obchod e", Obchod.class).getResultList();
    }                
    
    public static List<Folder> queryFilesystem(EntityManager em) {
        return em.createQuery("select e from Folder e", Folder.class).getResultList();
    }

}
