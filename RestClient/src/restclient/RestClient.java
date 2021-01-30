/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

/**
 *
 * @author vsa
 */
public class RestClient {
    public static void main(String[] args) {
        JerseyClient jc = new JerseyClient();
        System.out.println(jc.getSkuska(Skuska.class, "TZI").getDen());        
        jc.enrollStudent("92268", "TZI");
        System.out.println(jc.getSkuska(Skuska.class, "TZI").getStudent().size());        
    }
}
