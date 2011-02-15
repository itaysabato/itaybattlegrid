package battlegrid.players;

import battlegrid.abstracts.ActionHolder;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.Game;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Rotmus
 * Date: 26/01/2011
 * Time: 17:45:14
 * To change this template use File | Settings | File Templates.
 */
public class HumanPlayer implements Player {
    private Scanner scanner;

    public HumanPlayer(){
        scanner = new Scanner(System.in);
    }

    public void setAttributes(Map<String, String> playerAttributes) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void init(GameEntityInfo[][] gameState, GameEntityInfo myState) {}

    public void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo) {
        String act  = scanner.next();
        if(act.equals("1"))  toDo.setAction(Game.Action.TURN_LEFT);
        else if(act.equals("2"))  toDo.setAction(Game.Action.TURN_RIGHT);
        else if(act.equals("4"))  toDo.setAction(Game.Action.MOVE_FWD);
        else if(act.equals("3"))  toDo.setAction(Game.Action.SHOOT);
    }

    public void gameOver(boolean youWin) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
