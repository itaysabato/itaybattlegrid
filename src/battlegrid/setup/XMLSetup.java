package battlegrid.setup;

import battlegrid.game.GameView;
import battlegrid.game.view.GameViewImpl;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.Game;
import battlegrid.game.view.NullGameView;

import java.io.File;
import java.util.Arrays;

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
            XMLoader parser = new XMLoader(new File(PROPERTIES_PATH+File.separator+arguments[0]));
            properties.loadProperties(parser);

            Player[] players = makePlayers();
            int[] scores = new int[players.length];
            GameView view;
            int roundTime;

            if(Boolean.valueOf(properties.getProperty("Game.train"))){
                System.out.println("Starting Training Phase:");
                if(Boolean.valueOf(properties.getProperty("Train.showView"))){
                     view =  new GameViewImpl(scores);
                    roundTime = properties.getIntProperty("Train.roundTime");
                }
                else {
                       view = new NullGameView();
                       roundTime = 0;
                }
                System.out.println("view: <"+view.getClass().getSimpleName()+"> roundTime: <"+roundTime+">");
                runGames(players, scores, view,properties.getIntProperty("Train.numRounds"), roundTime);
            }

            Arrays.fill(scores, 0);
            System.out.println("Starting Final Phase:");
                if(Boolean.valueOf(properties.getProperty("Game.showView"))){
                     view =  new GameViewImpl(scores);
                    roundTime = properties.getIntProperty("Game.roundTime");
                }
                else {
                       view = new NullGameView();
                       roundTime = 0;
                }
                System.out.println("view: <"+view.getClass().getSimpleName()+"> roundTime: <"+roundTime+">");                            
                runGames(players, scores, view,properties.getIntProperty("Game.numRounds"), roundTime);
        }
        catch (RuntimeException e){
            System.err.println("runtime exception thrown:");
            e.printStackTrace();
        }
        catch (Exception e){
            System.err.println("checked exception thrown during setup:");
            e.printStackTrace();
        }
    }

    private static void runGames(Player[] players, int[] scores, GameView view, int numRounds, long roundTime) {
        GameProperties properties = getGameProperties();
        Game game = new Game(view);
        for(int i = 0; i < numRounds; i++){
            game.init(properties.getBoard(), players, roundTime, Boolean.valueOf(properties.getProperty("Random.players")));
            int winnerID = game.startGame();
            scores[winnerID]++;
            view.dispose();
//            System.out.println(winnerID);
        }
        for(int i = 0; i < scores.length; i++){
            System.out.println(properties.getPlayerAttribute(i,"Player.name")+
                    "("+properties.getPlayerAttribute(i,"Player.className")+")"
                    + " has a score of: "+scores[i]);
        }
    }

    private static Player[] makePlayers() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        GameProperties properties = getGameProperties();
        Player[] players = new Player[properties.getPlayerCount()];

        for(int i = 0; i < properties.getPlayerCount(); i++){
            String className = properties.getPlayerAttribute(i,"Player.className");
            players[i] = (Player) Class.forName(PLAYERS_PACK+className).newInstance();
            players[i].setAttributes(properties.getPlayerAttributes(i));
        }
        return players;
    }
}
