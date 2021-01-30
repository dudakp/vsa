/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxapp;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author igor
 */
public class SaxHandler extends DefaultHandler {

    private String name;
    private String elementValue;
    private String currentPrednasajuci;
    private String currentRocnik;
    private String currentPredmet;
    private boolean inStudium;
    private boolean inZameranie;
    private boolean inPredmet;
    private boolean inPrednasajuci;

    public SaxHandler(String name) {
        this.name = name;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.elementValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        final boolean prednasajuciPredicate
                = (this.inStudium && this.inZameranie && this.inPredmet && this.inPrednasajuci)
                && "meno".equals(qName);
        if ((this.inStudium && this.inZameranie && this.inPredmet) && "nazov".equals(qName)) {
            // nazov predmetu
            this.currentPredmet = this.elementValue;
        }
        if (prednasajuciPredicate) {
            this.currentPrednasajuci = this.elementValue;
        }
        if ("studium".equals(qName)) {
            this.inStudium = false;
        }
        if ("zameranie".equals(qName)) {
            this.inZameranie = false;
        }
        if ("nazov".equals(qName)) {            
            this.currentPrednasajuci = "";
        }
        if ("predmet".equals(qName)) {
            this.inPredmet = false;
            this.currentPrednasajuci = "";
        }
        if ("prednasajuci".equals(qName)) {
            this.inPrednasajuci = false;
        }
        if (prednasajuciPredicate && this.name.equals(this.currentPrednasajuci) && "Bc-1".equals(this.currentRocnik)) {
            System.out.println(this.currentPredmet);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("studium".equals(qName)) {
            this.inStudium = true;
        }
        if ("zameranie".equals(qName)) {
            this.inZameranie = true;
        }
        if ("predmet".equals(qName) && this.inZameranie) {
            this.inPredmet = true;
            int attLen = attributes.getLength();
            for (int i = 0; i < attLen; i++) {
                String attName = attributes.getQName(i);
                if ("rocnik".equals(attName)) {
                    this.currentRocnik = attributes.getValue(i);
                }
            }
        }
        if ("prednasajuci".equals(qName)) {
            this.inPrednasajuci = true;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startDocument() throws SAXException {
//        this.inStudium = false;
        this.inZameranie = false;
        this.inPredmet = false;
    }

}
