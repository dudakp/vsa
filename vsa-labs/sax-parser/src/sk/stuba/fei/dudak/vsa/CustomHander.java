/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stuba.fei.dudak.vsa;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sk.stuba.fei.dudak.vsa.model.Item;
import sk.stuba.fei.dudak.vsa.model.Recipe;
import sk.stuba.fei.dudak.vsa.model.RecipeBook;

/**
 *
 * @author pavol
 */
public class CustomHander extends DefaultHandler {

    private String elementValue;
    private RecipeBook recipeBook;
    private Recipe currentRecipe;
    private Item currentItem;
    private boolean inRecipe = false;
    private boolean inItem = false;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.elementValue = new String(ch, start, length);
    }

    @Override
    public void startDocument() throws SAXException {
        this.recipeBook = new RecipeBook();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("receptar".equals(qName)) {
            this.recipeBook.setRecipes(new ArrayList<>());
        }
        if ("recept".equals(qName)) {
            this.inRecipe = true;
            this.currentRecipe = new Recipe();
            this.currentRecipe.setItems(new ArrayList<>());
        }
        if ("polozka".equals(qName)) {
            this.inItem = true;
            this.currentItem = new Item();
        }
        if ("mnozstvo".equals(qName)) {
            int attLen = attributes.getLength();
            for (int i = 0; i < attLen; i++) {
                String attName = attributes.getQName(i);
                if ("jednotka".equals(attName)) {
                    this.currentItem.setUnit(attributes.getValue(i));
                }
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("nazov".equals(qName) && this.inRecipe && !this.inItem) {
            this.currentRecipe.setTitle(this.elementValue);
        }
        if ("nazov".equals(qName) && this.inItem && this.inRecipe) {
            this.currentItem.setTitle(this.elementValue);
        }
        if ("polozka".equals(qName)) {
            this.currentRecipe.getItems().add(this.currentItem);
            this.inItem = false;
            this.currentItem = null;
        }
        if ("recept".equals(qName)) {
            this.recipeBook.getRecipes().add(this.currentRecipe);
            this.inRecipe = false;
            this.currentRecipe = null;
        }
        if ("mnozstvo".equals(qName)) {
            this.currentItem.setQuantity(Double.parseDouble(this.elementValue));
        }

    }

    public RecipeBook getRecipeBook() {
        return recipeBook;
    }

    public void setRecipeBook(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

}
