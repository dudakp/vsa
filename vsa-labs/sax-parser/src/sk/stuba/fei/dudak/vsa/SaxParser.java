/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stuba.fei.dudak.vsa;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import sk.stuba.fei.dudak.vsa.model.Item;
import sk.stuba.fei.dudak.vsa.model.Recipe;
import sk.stuba.fei.dudak.vsa.model.RecipeBook;

/**
 *
 * @author pavol
 */
public class SaxParser {

    /**
     * @param args the command line arguments
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        CustomHander handler = new CustomHander();

        saxParser.parse("resources/CV11.xml", handler);
        RecipeBook book = handler.getRecipeBook();
        List<Recipe> flourRecipes = book.getRecipes().stream()
                .filter(recipe -> recipe.getItems().stream()
                        .anyMatch(item -> "muka".equals(item.getTitle())))
                .collect(Collectors.toList());
        System.out.println("Recepty obsahujuce muku: " + flourRecipes);
        Item milkOmeleteItem = book.getRecipes().stream()
                .filter(recipe -> "omeleta".equals(recipe.getTitle()))
                .findFirst()
                .orElseThrow(() -> new Exception("omeleta nie je v receptari"))
                .getItems().stream()
                .filter(item -> "mlieko".equals(item.getTitle()))
                .findFirst()
                .orElseThrow(() -> new Exception("v omelete nie je mlieko!"));
        System.out.println("Mlieko v omelete: " + milkOmeleteItem.toString());

    }

}
