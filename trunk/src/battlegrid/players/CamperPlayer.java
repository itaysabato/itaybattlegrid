package battlegrid.players;

import battlegrid.abstracts.*;
import battlegrid.game.execution.Game;

import java.util.Map;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 28/01/2011 <br/>
 * Time: 18:13:29 <br/>
 */
public class CamperPlayer implements Player {

    private int turns;

    public void setAttributes(Map<String, String> playerAttributes) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void init(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        turns = 0;
    }

    public void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo) {
        int x = myState.getX();
        int y = myState.getY();
        do{
            x += myState.getDirection().dx;
            y += myState.getDirection().dy;
        } while(gameState[y][x].getType().equals(GameEntityType.BLANK));
        if(gameState[y][x].getLife() > 0) {
            toDo.setAction(Game.Action.SHOOT);
        }
        else {
            boolean isFrontClear = gameState[myState.getDirection().dy + myState.getY()][myState.getDirection().dx + myState.getX()].getType().equals(GameEntityType.BLANK);
            if(turns < Direction.values().length || !isFrontClear){
                toDo.setAction(Game.Action.TURN_LEFT);
                turns++;
            }
            else {
                toDo.setAction(Game.Action.MOVE_FWD);
                turns = 0;
            }
        }
    }

    public void gameOver(boolean youWin) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
