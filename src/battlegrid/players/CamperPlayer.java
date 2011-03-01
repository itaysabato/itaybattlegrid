package battlegrid.players;

import battlegrid.abstracts.*;
import battlegrid.game.execution.Game;

import java.util.Map;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 01/03/2011 <br/>
 * Time: 22:29:52 <br/>
 */
public class CamperPlayer implements Player {
    public void setAttributes(Map<String, String> playerAttributes) {
    }

    public void init(GameEntityInfo[][] gameState, GameEntityInfo myState) {
    }

    public void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo) {

        GameEntityInfo enemy = findEnemy(gameState,myState);
        int myX = myState.getX(), myY = myState.getY();
        int enemyX =  enemy.getX(), enemyY = enemy.getY();
        Direction wantedDirection = Direction.NORTH;
        Game.Action wantedAction = Game.Action.NO_OP;

        if(myX<enemyX){
            if(myY<enemyY) wantedDirection = Direction.SOUTH_EAST;
            else if(myY==enemyY) wantedDirection = Direction.EAST;
            else wantedDirection = Direction.NORTH_EAST;
        }
        else if(myX>enemyX) {
            if(myY<enemyY) wantedDirection = Direction.SOUTH_WEST;
            else if(myY==enemyY) wantedDirection = Direction.WEST;
            else wantedDirection = Direction.NORTH_WEST;
        }
        else {
            wantedDirection = (enemyY<myY)?Direction.NORTH:Direction.SOUTH;
        }

        if(Math.abs(myState.getDirection().ordinal()-wantedDirection.ordinal())<=(Direction.values().length/2)) {
            if(myState.getDirection().ordinal()<wantedDirection.ordinal()) wantedAction = Game.Action.TURN_RIGHT;
            else if(myState.getDirection().ordinal()>wantedDirection.ordinal()) wantedAction = Game.Action.TURN_LEFT;
        }
        else{
            if(myState.getDirection().ordinal()<wantedDirection.ordinal()) wantedAction = Game.Action.TURN_LEFT;
            else if(myState.getDirection().ordinal()>wantedDirection.ordinal()) wantedAction = Game.Action.TURN_RIGHT;
        }

        if(calcLocked(gameState,myState)){
            wantedAction = Game.Action.SHOOT;
        }
        toDo.setAction(wantedAction);
    }

    private boolean calcLocked(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        int x = myState.getX();
        int y = myState.getY();
        do{
            x += myState.getDirection().dx;
            y += myState.getDirection().dy;
        } while(gameState[y][x].getType().equals(GameEntityType.BLANK));
        if(gameState[y][x].getType().equals(GameEntityType.PLAYER)){
            return true;
        }
        else {
            return false;
        }
    }

    private GameEntityInfo findEnemy(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        for(int i = 0; i<gameState.length;  i++){
            for(int j = 0; j<gameState[0].length; j++){
                if(gameState[i][j].getType().equals(GameEntityType.PLAYER) && gameState[i][j]!=myState){
                    return gameState[i][j];
                }
            }
        }
        return null;
    }

    public void gameOver(boolean youWin) {
    }
}
