package battlegrid.players;

import battlegrid.abstracts.Direction;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.GameEntityType;
import battlegrid.game.execution.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 08/02/2011 <br/>
 * Time: 15:49:58 <br/>
 */
public class AStarStateFactory implements AStarPlayer.StateFactory {

    public List<AStarPlayer.State> getInitialStates(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        List<AStarPlayer.State> states = new ArrayList<AStarPlayer.State>();

        for(Game.Action action : Game.Action.values()){
            states.add(new AStarState(copy(gameState), new MyEntityInfo(myState), findEnemy(gameState,myState),action, action,0));
        }
        return states;
    }

    private MyEntityInfo findEnemy(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        for(int i = 0; i<gameState.length;  i++){
            for(int j = 0; j<gameState[0].length; j++){
                if(gameState[i][j].getType().equals(GameEntityType.PLAYER) && gameState[i][j]!=myState){
                    return new MyEntityInfo(gameState[i][j]);
                }
            }
        }
        return null;
    }

    private static MyEntityInfo[][] copy(GameEntityInfo[][] gameState) {
        MyEntityInfo[][] duplicate = new MyEntityInfo[gameState.length][gameState[0].length];

        for(int i = 0; i < gameState.length; i++){
            for(int j = 0; j < gameState[i].length; j++){
                duplicate[i][j] = new MyEntityInfo(gameState[i][j]);
            }
        }
        return duplicate;
    }

    private static class  AStarState implements AStarPlayer.State {

        private Game.Action rootAction;
        private int heuristicValue;
        private int pathValue;
        private  MyEntityInfo[][] gameState;
        private MyEntityInfo myState;
        private MyEntityInfo enemy;


        public AStarState(MyEntityInfo[][] gameState, MyEntityInfo myState, MyEntityInfo enemy,Game.Action root, Game.Action toDo, int pathValue) {
            this.pathValue = pathValue;
            rootAction = root;
            this.gameState = gameState;
            int x = myState.getX(), y = myState.getY();
            int enemyX = enemy.getX(), enemyY = enemy.getY();
            Direction dir = myState.getDirection();
            this.myState = gameState[y][x];
            this.enemy = gameState[enemyY][enemyX];

            switch (toDo) {
                case TURN_RIGHT:
                    int i = (this.myState.getDirection().ordinal() + 1) % Direction.values().length;
                    this.myState.setDirection(Direction.values()[i]);
                    break;
                case TURN_LEFT:
                    i = (this.myState.getDirection().ordinal() - 1) % Direction.values().length;
                    i = (Direction.values().length + i) % Direction.values().length;
                    this.myState.setDirection(Direction.values()[i]);
                    break;
                case MOVE_FWD:
                    if(gameState[y+dir.dy][x+dir.dx].getType().equals(GameEntityType.BLANK)) {
                        gameState[y+dir.dy][x+dir.dx] = gameState[y][x];
                        this.myState.setXY(x+dir.dx,y+dir.dy);
                        gameState[y][x] = new MyEntityInfo(x,y);
                    }
                    break;
                case SHOOT:
                    int dy = enemy.getY()-y, dx = enemy.getX()-x;
                    if((Math.abs(dy)==Math.abs(dx)) && (Math.abs(dy)/dy==dir.dy)  && (Math.abs(dx)/dx==dir.dx))  {
                        enemy.setLife(enemy.getLife()-1);
                    }
                    break;
            }
            heuristicValue = calculateHeuristic();
        }

        private int calculateHeuristic() {
            if(myState.getLife()<=0) {
                return Integer.MAX_VALUE -1000;
            }
            int dy = Math.abs(enemy.getY()-myState.getY());
            int dx = Math.abs(enemy.getX()-myState.getX());
            int dxy = Math.abs(dx-dy);
            return   Math.min(Math.min(dx,dy),dxy)+enemy.getLife();
        }

        public Game.Action getRootAction() {
            return rootAction;
        }

        public List<AStarPlayer.State> spawn() {
            List<AStarPlayer.State> states = new ArrayList<AStarPlayer.State>();

            for(Game.Action action : Game.Action.values()){
                states.add(new AStarState(copy(gameState), new MyEntityInfo(myState), new MyEntityInfo(enemy), rootAction, action,pathValue+1));
            }
            return states;
        }

        public int getHeuristicValue() {
            return heuristicValue;
        }

        public int getPathValue() {
            return pathValue;
        }

        public int compareTo(AStarPlayer.State other) {
            return heuristicValue + pathValue - other.getHeuristicValue() - other.getPathValue();
        }
    }

    private static class MyEntityInfo implements GameEntityInfo {
        private final long ID;
        private final GameEntityType TYPE;
        private int life;
        private Direction direction;
        private int x;
        private int y;
        private boolean hasDirection;
        private boolean canShoot;

        public MyEntityInfo(int x, int y) {
            ID = 0;
            TYPE = GameEntityType.BLANK;
            this.canShoot = false;
            this.hasDirection = false;
            this.x = x;
            this.y = y;
            direction = null;
            life = 0;
        }

        public MyEntityInfo(GameEntityInfo source) {
            ID = source.getID();
            TYPE = source.getType();
            this.canShoot = source.canShoot();
            this.hasDirection = source.hasDirection();
            setDirection(source.getDirection());
            setXY(source.getX(),source.getY());
            setLife(source.getLife());
        }

        public MyEntityInfo(GameEntityType type, long id, int x, int y, int life, boolean canShoot, boolean hasDirection) {
            ID = id;
            TYPE = type;
            this.canShoot = canShoot;
            this.hasDirection = hasDirection;
            setDirection(direction);
            setXY(x,y);
            setLife(life);
        }

        public long getID() {
            return ID;
        }

        public GameEntityType getType() {
            return TYPE;
        }

        public  void setLife(int life) {
            this.life = life;
        }

        public int getLife() {
            return life;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setXY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean canShoot() {
            return canShoot;
        }

        public boolean hasDirection() {
            return hasDirection;
        }
    }
}
