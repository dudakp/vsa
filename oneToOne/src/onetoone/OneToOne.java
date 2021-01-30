/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onetoone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import onetoone.entities.Address;
import onetoone.entities.Users;

/**
 *
 * @author vsa
 */
public class OneToOne {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("oneToOnePU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        final Users user = new Users();
        final Address address = new Address();
        address.setUser(user);
        address.setStreet("Groove st.");
        user.setAddress(address);        
        
        em.persist(user);
        em.getTransaction().commit();
        
        final Users find = em.find(Users.class, 1L);
        System.out.println(find);
    }
    
}
