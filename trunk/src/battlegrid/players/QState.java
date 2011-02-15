package battlegrid.players;

import battlegrid.abstracts.Direction;
import battlegrid.abstracts.GameEntityInfo;

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

    public QState(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        
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
