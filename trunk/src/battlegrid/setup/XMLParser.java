package battlegrid.setup;

import static battlegrid.setup.GameProperties.*;
import battlegrid.abstracts.GameEntityType;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 27/01/2011 <br/>
 * Time: 23:59:06 <br/>
 */
public class XMLParser {
    private Element properties;

    public XMLParser(File input) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        properties = factory.newDocumentBuilder().parse(input).getDocumentElement();
    }

    public GameEntityType[][] loadBoard() {
        GameEntityType[][] board;
        NodeList rows = ((Element) properties.getElementsByTagName("Board").item(0)).getElementsByTagName("Row");
        board = new GameEntityType[rows.getLength()][];

        for(int i = 0; i < rows.getLength(); i++){
            if(rows.item(i).getNodeType() == Node.ELEMENT_NODE){
                board[i] = loadRow((Element) rows.item(i));
            }
        }
        return board;
    }

    private GameEntityType[] loadRow(Element rowElement) {
        NodeList nodes = rowElement.getChildNodes();
        GameEntityType[] row = new GameEntityType[nodes.getLength()];

        for(int i = 0; i < nodes.getLength(); i++){
            row[i] = GameEntityType.valueOf(nodes.item(i).getNodeName());
        }
        return row;
    }

    private interface Putter<T> {
        void put(Map<String, T> map, String key, String value);
    }

    public Map<String, String> loadMisc() {
        return load("Misc", new Putter<String>() {
            public void put(Map<String, String> map, String key, String value) {
                map.put(key, value);
            }
        });
    }

    public Map<String, ImageIcon> loadImages() {
        return load("Images", new Putter<ImageIcon>() {
            public void put(Map<String, ImageIcon> map, String key, String value) {
                map.put(key, new ImageIcon(IMAGES_PATH+File.separator+value));
            }
        });
    }

    private <T> Map<String, T> load(String tagName, Putter<T> putter) {
        Map<String,T> map = new HashMap<String, T>();
        NodeList elements = properties.getElementsByTagName(tagName).item(0).getChildNodes();

        for(int i = 0; i < elements.getLength(); i++){
            if(elements.item(i).getNodeType() == Node.ELEMENT_NODE){
                putAttributes(map, (Element) elements.item(i), putter);
            }
        }
        return map;
    }

    private <T> void putAttributes(Map<String, T> map, Element element, Putter<T> putter) {
        NamedNodeMap attributes = element.getAttributes();
        for(int j = 0; j <attributes.getLength();  j++){
            Node attr = attributes.item(j);
            putter.put(map,element.getNodeName()+"."+attr.getNodeName(),  attr.getNodeValue());
        }
    }

    public List<Map<String, String>> loadPlayers() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        NodeList elements = properties.getElementsByTagName("Players").item(0).getChildNodes();

        for(int i = 0; i < elements.getLength(); i++){
            if(elements.item(i).getNodeType() == Node.ELEMENT_NODE){
                Map<String, String> map = new HashMap<String, String>();

                putAttributes(map, (Element) elements.item(i), new Putter<String>() {
                    public void put(Map<String, String> map, String key, String value) {
                        map.put(key, value);
                    }
                });
                list.add(map);
            }
        }
        return list;
    }
}
