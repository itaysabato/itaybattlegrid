package battlegrid.players;

import battlegrid.abstracts.*;
import battlegrid.game.execution.Game;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 28/01/2011 <br/>
 * Time: 18:13:29 <br/>
 */
public class CamperPlayer implements Player {

    private int turns;

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
        else if(turns < Direction.values().length){
            toDo.setAction(Game.Action.TURN_LEFT);
            turns++;
        }
        else {
            toDo.setAction(Game.Action.MOVE_FWD);
             turns = 0;
        }
    }
}