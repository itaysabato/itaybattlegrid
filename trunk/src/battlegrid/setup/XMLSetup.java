package battlegrid.setup;

import battlegrid.abstracts.GameEntityInfo;
import battlegrid.game.GameView;
import battlegrid.game.execution.PlayerEntity;
import battlegrid.game.view.GameViewImpl;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.Game;
import battlegrid.game.view.NullGameView;

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

            GameView view =  Boolean.valueOf(properties.getProperty("Game.showView")) ? new GameViewImpl() : new NullGameView();
            Game game = new Game(view);
            Player[] players = makePlayers();
             int[] scores = new int[players.length];

            for(int i = 0; i < properties.getIntProperty("Game.numRounds"); i++){
                game.init(properties.getBoard(), players);
                int winnerID = game.startGame();
                scores[winnerID]++;
                view.dispose();
            }

            for(int i = 0; i < scores.length; i++ ){
                System.out.println(properties.getPlayerAttribute(i,"Player.name")+
                        "("+properties.getPlayerAttribute(i,"Player.className")+")"
                + " has a score of: "+scores[i]);
            }
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
