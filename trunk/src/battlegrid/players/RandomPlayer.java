package battlegrid.players;

import battlegrid.abstracts.ActionHolder;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.Game;

import java.util.Map;
import java.util.Random;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 17:03:10 <br/>
 */
public class RandomPlayer implements Player  {
    private Random generator;

    public  RandomPlayer(){
        generator = new Random();
    }

    public void setAttributes(Map<String, String> playerAttributes) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void init(GameEntityInfo[][] gameState, GameEntityInfo myState) {}

    public void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo) {
        int x  = generator.nextInt(Game.Action.values().length - 1);
        toDo.setAction(Game.Action.values()[x+1]);
    }

    public void gameOver(boolean youWin) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
