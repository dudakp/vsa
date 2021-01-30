/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p9restapp2client;

import proxy.NewJerseyClient;

/**
 *
 * @author igor
 */
public class P9restapp2client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NewJerseyClient client = new NewJerseyClient();
        System.out.println(client.getMenu());
        System.out.println(client.getJedlo("Syr"));
        client.pridajJedlo("Bryndzove halusky: 3.20", "Halusky");
        client.pridajJedlo("Vyprazany syr: 0.20", "Syr");
        System.out.println(client.getMenu());
        System.out.println(client.getJedlo("Syr"));
        System.out.println(client.getJedlo("Halusky"));
        client.odstranJedlo("Syr");
        System.out.println(client.getMenu());
    }
    
}
