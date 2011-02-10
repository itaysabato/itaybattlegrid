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
            MyEntityInfo[][] gameCopy = copy(gameState);
            int[] enemyPos = findEnemy(gameState,myState);
            int[] myPos = {myState.getX(),myState.getY()};
            states.add(new AStarState(gameCopy, myPos, enemyPos ,action, action,0));
        }
        return states;
    }

    private int[] findEnemy(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        for(int i = 0; i<gameState.length;  i++){
            for(int j = 0; j<gameState[0].length; j++){
                if(gameState[i][j].getType().equals(GameEntityType.PLAYER) && gameState[i][j]!=myState){
                    int[] pos = {gameState[i][j].getX(), gameState[i][j].getY()};
                    return pos;
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


        public AStarState(MyEntityInfo[][] gameState, int[] myPos, int[] enemyPos,Game.Action root, Game.Action toDo, int pathValue) {
            this.pathValue = pathValue;
            rootAction = root;
            this.gameState = gameState;
            this.myState = gameState[myPos[1]][myPos[0]];
            this.enemy = gameState[enemyPos[1]][enemyPos[0]];

            Direction dir = this.myState.getDirection();
            int x = myState.getX(), y = myState.getY();
            int enemyX = enemy.getX(), enemyY = enemy.getY();
            boolean goal = false;
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
                    int row = y+dir.dy;
                    int col = x+dir.dx;
                    while(gameState[row][col].getType().equals(GameEntityType.BLANK)){
                        row += dir.dy;
                        col += dir.dx;
                    }

                    if(row==enemyY && col==enemyX){
                        enemy.setLife(enemy.getLife()-1);
                        goal = true;
                    }
                    else if(gameState[row][col].getType().equals(GameEntityType.WALL)){
                        gameState[row][col].setLife(gameState[row][col].getLife()-1);
                        if(gameState[row][col].getLife()<=0){
                            gameState[row][col] = new MyEntityInfo(col,row);     
                        }
                    }
                    break;
            }
            heuristicValue = goal?0:calculateHeuristic();
        }

        private int calculateHeuristic() {
            if(myState.getLife()<=0) {
                return Integer.MAX_VALUE -1000;
            }
            int dy = Math.abs(enemy.getY()-myState.getY());
            int dx = Math.abs(enemy.getX()-myState.getX());
            int dxy = Math.abs(dx-dy);

            return calc(Math.min(Math.min(dx,dy),dxy),dx,dy,dxy)+enemy.getLife();
        }

        private int calc(int min, int dx, int dy, int dxy) {
            int myDir =myState.getDirection().ordinal();
            Direction wantedDir = Direction.NORTH;
            if(dx==min){
                if(dx==0){
                    wantedDir = (enemy.getY()<myState.getY())?Direction.NORTH:Direction.SOUTH;
                }
                else{
                    if(enemy.getX()>myState.getX()) {
                        wantedDir = (enemy.getY()<myState.getY())?Direction.NORTH_EAST:Direction.SOUTH_EAST;
                    }
                    else{
                        wantedDir = (enemy.getY()<myState.getY())?Direction.NORTH_WEST:Direction.SOUTH_WEST;
                    }
                }
            }
            else if(dy==min){
                if(dy==0){
                    wantedDir = (enemy.getX()>myState.getX())?Direction.EAST:Direction.WEST;
                }
                else{
                    if(enemy.getY()<myState.getY()) {
                        wantedDir = (enemy.getX()>myState.getX())?Direction.NORTH_EAST:Direction.NORTH_WEST;                    }
                    else{
                        wantedDir = (enemy.getX()>myState.getX())?Direction.SOUTH_EAST:Direction.SOUTH_WEST;
                    }
                }
            }
            else if(dxy==min){
                if(dxy==0){
                    if(enemy.getY()<myState.getY()) {
                        wantedDir = (enemy.getX()>myState.getX())?Direction.NORTH_EAST:Direction.NORTH_WEST;                    }
                    else{
                        wantedDir = (enemy.getX()>myState.getX())?Direction.SOUTH_EAST:Direction.SOUTH_WEST;
                    }
                }
                else{
                    if(dx<dy) {
                        wantedDir = (enemy.getY()<myState.getY())?Direction.NORTH:Direction.SOUTH;                    }
                    else{
                        wantedDir = (enemy.getX()>myState.getX())?Direction.EAST:Direction.WEST;
                    }
                }
            }
            int dDir = Math.abs(myDir-wantedDir.ordinal());
            int rotate =  (myDir<wantedDir.ordinal())? Math.min(dDir, Math.abs(myDir+Direction.values().length-wantedDir.ordinal())):
                    Math.min(dDir, Math.abs(wantedDir.ordinal()+Direction.values().length-myDir));

            return min+rotate;
        }

        public Game.Action getRootAction() {
            return rootAction;
        }

        public List<AStarPlayer.State> spawn() {
            List<AStarPlayer.State> states = new ArrayList<AStarPlayer.State>();
            if(heuristicValue==0) return states;

            int[] myPos =  {myState.getX(), myState.getY()};
            int[] enemyPos =  {enemy.getX(), enemy.getY()};
            for(Game.Action action : Game.Action.values()){
                states.add(new AStarState(copy(gameState), myPos, enemyPos, rootAction, action,pathValue+1));
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
            this.direction = source.getDirection();
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
