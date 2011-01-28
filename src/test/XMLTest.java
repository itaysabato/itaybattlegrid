package test;

import static battlegrid.setup.GameProperties.*;
import battlegrid.setup.XMLParser;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 28/01/2011 <br/>
 * Time: 15:37:57 <br/>
 */
public class XMLTest extends TestCase {

    public void testMisc() throws IOException, SAXException, ParserConfigurationException {
        XMLParser parser = new XMLParser(new File(PROPERTIES_PATH+File.separator+"game.xml"));
        Map<String, String> miscProperties = parser.loadMisc();
        System.out.println(miscProperties);
    }

     public void testImages() throws IOException, SAXException, ParserConfigurationException {
         XMLParser parser = new XMLParser(new File(PROPERTIES_PATH+File.separator+"game.xml"));
        Map<String, ImageIcon> images = parser.loadImages();
        System.out.println(images);
    }

     public void testPlayers() throws IOException, SAXException, ParserConfigurationException {
         XMLParser parser = new XMLParser(new File(PROPERTIES_PATH+File.separator+"game.xml"));
        List<Map<String, String>> list = parser.loadPlayers();
        System.out.println(list);
    }
}
