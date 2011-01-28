package battlegrid.setup;

import battlegrid.players.*;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.Game;
import battlegrid.game.view.GameGUI;

import java.io.File;

import static battlegrid.setup.GameProperties.*;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 28/01/2011 <br/>
 * Time: 00:42:15 <br/>
 */
public class XMLSetup {
    public static void main(String[] arguments) {
        try {
            GameProperties properties = getGameProperties();
            XMLParser parser = new XMLParser(new File(PROPERTIES_PATH+File.separator+arguments[0]));
            properties.loadProperties(parser);
            GameGUI.InitializeMaps();
            Player[] players = makePlayers();

            GameGUI view = new  GameGUI();
            Game game = new Game(view);
            game.init(properties.getBoard(), players);
            int winnerID = game.startGame();
            System.out.println("The winner is " + properties.getPlayerAttribute(winnerID,"Player.name") );
            view.dispose();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Player[] makePlayers() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        GameProperties properties = getGameProperties();
        Player[] players = new Player[properties.getPlayerCount()];

        for(int i = 0; i < properties.getPlayerCount(); i++){
            String className = properties.getPlayerAttribute(i,"Player.className");
            players[i] = (Player) Class.forName(PLAYERS_PACK+className).newInstance();
        }
        return players;
    }
}
