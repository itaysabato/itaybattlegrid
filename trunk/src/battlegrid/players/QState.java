package battlegrid.players;

import battlegrid.abstracts.Direction;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.GameEntityType;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 15/02/2011 <br/>
 * Time: 22:00:35 <br/>
 */
public class QState {

    private final short myDirection;
    private final short hisDirection;
    private final short isLockedOnHim;
    private final short isLockedOnMe;
    private final short lifeDiff;
    private final short quarter;
    private final short frontWall;
    private final short leftWall;
    private final short rightWall;
    private static final short NORTH_EAST = 0;
    private static final short SOUTH_EAST = 1;
    private static final short SOUTH_WEST = 2;
    private static final short NORTH_WEST = 3;
    private static final short WIN = 0;
    private static final short TIE = 1;
    private static final short LOSS = 2;

    public QState(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        GameEntityInfo enemy = findEnemy(gameState,myState);
        myDirection = calcDirection(gameState, myState, enemy ,true);
        hisDirection = calcDirection(gameState, myState, enemy, false);
        isLockedOnMe = calcLocked(gameState, myState, enemy, true);
        isLockedOnHim = calcLocked(gameState, myState, enemy, false);
        lifeDiff = calcLifeDiff(gameState, myState, enemy);
        quarter = calcQuarter(gameState, myState, enemy);
        frontWall = calcWall(gameState, myState, enemy, Direction.NORTH);
        leftWall = calcWall(gameState, myState, enemy, Direction.WEST);
        rightWall = calcWall(gameState, myState, enemy, Direction.EAST);
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


    private short calcWall(GameEntityInfo[][] gameState, GameEntityInfo myState, GameEntityInfo enemy, Direction direction) {
        Direction[] values = Direction.values();
        int myX = myState.getX(), myY = myState.getY() ;
        Direction myDirection = myState.getDirection();

        if(direction.equals(Direction.NORTH)){
            GameEntityInfo front = gameState[myY+myDirection.dy][myX+myDirection.dx];

            if(front.equals(GameEntityType.WALL) || front.equals(GameEntityType.BORDER)) return 1;
            else  return 0;
        }
        else if(direction.equals(Direction.EAST)){
            myDirection = values[(myDirection.ordinal()+1)%values.length];
            GameEntityInfo right = gameState[myY+myDirection.dy][myX+myDirection.dx];

            if(right.equals(GameEntityType.WALL) || right.equals(GameEntityType.BORDER)) return 1;
            else return 0;
        }
        else if(direction.equals(Direction.WEST)) {
             myDirection = values[(myDirection.ordinal()-1+values.length)%values.length];
            GameEntityInfo left = gameState[myY+myDirection.dy][myX+myDirection.dx];

            if(left.equals(GameEntityType.WALL) || left.equals(GameEntityType.BORDER)) return 1;
            else return 0;
        }

        return 0;
    }

    private short calcQuarter(GameEntityInfo[][] gameState, GameEntityInfo myState, GameEntityInfo enemy)
    {
        int myX = myState.getX(), myY = myState.getY() ;
        int enemyX = enemy.getX(), enemyY = enemy.getY();

        if(enemyX>=myX && enemyY<myY) return NORTH_EAST;
        else if(enemyX>myX && enemyY>=myY) return SOUTH_EAST;
        else if(enemyX<=myX && enemyY>myY) return SOUTH_WEST;
        else if(enemyX<myX && enemyY<=myY) return NORTH_WEST;
        return 0;
    }

    private short calcLifeDiff(GameEntityInfo[][] gameState, GameEntityInfo myState, GameEntityInfo enemy) {
        if(myState.getLife()>enemy.getLife())  return WIN;
        else if(myState.getLife()>enemy.getLife()) return TIE;
        else return LOSS;
    }

    private short calcLocked(GameEntityInfo[][] gameState, GameEntityInfo myState, GameEntityInfo enemy, boolean my) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    private short calcDirection(GameEntityInfo[][] gameState, GameEntityInfo myState, GameEntityInfo enemy, boolean my) {
        Direction direction = (my) ? myState.getDirection() : enemy.getDirection();

        if(Direction.NORTH.ordinal()<=direction.ordinal() && direction.ordinal()<Direction.EAST.ordinal()) {
            return NORTH_EAST;
        }
        if(Direction.EAST.ordinal()<=direction.ordinal() && direction.ordinal()<Direction.SOUTH.ordinal()) {
            return SOUTH_EAST;
        }
        if(Direction.SOUTH.ordinal()<=direction.ordinal() && direction.ordinal()<Direction.WEST.ordinal()) {
            return SOUTH_WEST;
        }
        if(Direction.WEST.ordinal()<=direction.ordinal() && direction.ordinal()<Direction.NORTH.ordinal()) {
            return NORTH_WEST;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return 31*myDirection +  961*hisDirection + 29791*isLockedOnMe + 923521*isLockedOnHim
                + 28629151*lifeDiff + 887503681*quarter + ((7 << 5) - 7)*frontWall +  ((8 << 5) - 8)*leftWall + ((9 << 5) - 9)*rightWall;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof QState){
            QState other = (QState) obj;
            return other.frontWall == frontWall && other.hisDirection == hisDirection
                    && other.isLockedOnHim == isLockedOnHim && other.isLockedOnMe == isLockedOnMe
                    && other.leftWall == leftWall && other.lifeDiff == lifeDiff && other.myDirection == myDirection;
        }
        else return false;
    }
}
