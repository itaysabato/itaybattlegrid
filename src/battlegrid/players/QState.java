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

    static final short NORTH_EAST = 0;
    static final short SOUTH_EAST = 1;
    static final short SOUTH_WEST = 2;
    static final short NORTH_WEST = 3;
    static final short ADVANTAGE = 0;
    static final short TIE = 1;
    static final short NEG = 2;

    final short myDirection;
    final short hisDirection;
    final short isLockedOnHim;
    final short isLockedOnMe;
    final short lifeDiff;
    final short quarter;
    final short frontWall;
    final short leftWall;
    final short rightWall;

    public QState(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        GameEntityInfo enemy = findEnemy(gameState,myState);
        myDirection = calcDirection(myState, enemy ,true);
        hisDirection = calcDirection(myState, enemy, false);
        isLockedOnMe = calcLocked(gameState, enemy);
        isLockedOnHim = calcLocked(gameState, myState);
        lifeDiff = calcLifeDiff(myState, enemy);
        quarter = calcQuarter(myState, enemy);
        frontWall = calcWall(gameState, myState, Direction.NORTH);
        leftWall = calcWall(gameState, myState, Direction.WEST);
        rightWall = calcWall(gameState, myState, Direction.EAST);
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


    private short calcWall(GameEntityInfo[][] gameState, GameEntityInfo myState, Direction direction) {
        Direction[] values = Direction.values();
        int myX = myState.getX(), myY = myState.getY() ;
        Direction myDirection = myState.getDirection();

        if(direction.equals(Direction.NORTH)){
            GameEntityInfo front = gameState[myY+myDirection.dy][myX+myDirection.dx];

            if(front.getType().equals(GameEntityType.WALL) || front.getType().equals(GameEntityType.BORDER)) return 1;
            else  return 0;
        }
        else if(direction.equals(Direction.EAST)){
            myDirection = values[(myDirection.ordinal()+1)%values.length];
            GameEntityInfo right = gameState[myY+myDirection.dy][myX+myDirection.dx];

            if(right.getType().equals(GameEntityType.WALL) || right.getType().equals(GameEntityType.BORDER)) return 1;
            else return 0;
        }
        else if(direction.equals(Direction.WEST)) {
            myDirection = values[(myDirection.ordinal()-1+values.length)%values.length];
            GameEntityInfo left = gameState[myY+myDirection.dy][myX+myDirection.dx];

            if(left.getType().equals(GameEntityType.WALL) || left.getType().equals(GameEntityType.BORDER)) return 1;
            else return 0;
        }

        return 0;
    }

    private short calcQuarter(GameEntityInfo myState, GameEntityInfo enemy)
    {
        int myX = myState.getX(), myY = myState.getY() ;
        int enemyX = enemy.getX(), enemyY = enemy.getY();

        if(enemyX>=myX && enemyY<myY) return NORTH_EAST;
        else if(enemyX>myX && enemyY>=myY) return SOUTH_EAST;
        else if(enemyX<=myX && enemyY>myY) return SOUTH_WEST;
        else if(enemyX<myX && enemyY<=myY) return NORTH_WEST;
        return 0;
    }

    private short calcLifeDiff(GameEntityInfo myState, GameEntityInfo enemy) {
        if(myState.getLife()>enemy.getLife())  return ADVANTAGE;
        else if(myState.getLife()>enemy.getLife()) return TIE;
        else return NEG;
    }

    private short calcLocked(GameEntityInfo[][] gameState, GameEntityInfo shooter) {
        int x = shooter.getX();
        int y = shooter.getY();
        do{
            x += shooter.getDirection().dx;
            y += shooter.getDirection().dy;
        } while(gameState[y][x].getType().equals(GameEntityType.BLANK));
        if(gameState[y][x].getType().equals(GameEntityType.PLAYER)){
            return 1;
        }
        else {
            return 0;
        }
    }

    private short calcDirection(GameEntityInfo myState, GameEntityInfo enemy, boolean my) {
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
