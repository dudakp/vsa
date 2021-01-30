/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvicenie2;

import java.util.HashMap;

/**
 *
 * @author vsa
 */
public class Cvicenie2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Projekt p = new Projekt();
        System.out.println(p.aktializujKnihu("isbn123", "LoTR", 420.69));
        System.out.println(p.aktializujKnihu("isbn321", "Hobbit", 666.0));
        
        final HashMap<String, Double> cennik = new HashMap<>();
        cennik.put("isbn123", 123.1);
        cennik.put("isbn321", 0.1);
        p.aktualizujCeny(cennik);
    }
    
    
}
