package battlegrid.players;

import battlegrid.abstracts.ActionHolder;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.GameEntityType;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.Game;

import java.util.Map;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 30/01/2011 <br/>
 * Time: 23:52:00 <br/>
 */
public class CowardlyPlayer implements Player {

    public void setAttributes(Map<String, String> playerAttributes) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void init(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo) {
          if(calcLocked(gameState, myState)){
              toDo.setAction(Game.Action.SHOOT);
          }
//        else if()
    }


        private boolean calcLocked(GameEntityInfo[][] gameState, GameEntityInfo shooter) {
        int x = shooter.getX();
        int y = shooter.getY();
        do{
            x += shooter.getDirection().dx;
            y += shooter.getDirection().dy;
        } while(gameState[y][x].getType().equals(GameEntityType.BLANK));
        if(gameState[y][x].getType().equals(GameEntityType.PLAYER)){
            return true;
        }
        else {
            return false;
        }
    }

    public void gameOver(boolean youWin) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
