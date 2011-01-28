package battlegrid.setup;

import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.GameEntityType;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 26/01/2011 <br/>
 * Time: 18:09:33 <br/>
 */
public class GameProperties {

    public static final String RES_PATH = "resources";
    public static final String PROPERTIES_PATH = RES_PATH+ File.separator+"properties";
    public static final String IMAGES_PATH = RES_PATH+ File.separator+"images";
    public static final String PLAYERS_PACK = "battlegrid.players.";

    private static GameProperties instance = new GameProperties();

    private GameEntityType[][] board;
    private Map<String, String> misc;
    private Map<String, ImageIcon> images;
    private List<Map<String, String>> playersAttributes;

    private  GameProperties(){}

    public static GameProperties getGameProperties() {
        return instance;
    }

    public void  loadProperties(XMLParser parser) {
        board = parser.loadBoard();
        misc = parser.loadMisc();
        images = parser.loadImages();
        playersAttributes = parser.loadPlayers();
    }

    public int getPlayerCount() {
        return playersAttributes.size();
    }

    public String getPlayerAttribute(int i, String key) {
        return playersAttributes.get(i).get(key);
    }

    public Map<String, String> getPlayerAttributes(int i) {
        return playersAttributes.get(i);
    }

    public GameEntityType[][] getBoard(){
        return board;
    }

    public ImageIcon getImage(String key) {
        return images.get(key);
    }

    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public String getProperty(String key) {
        return misc.get(key);
    }
}
