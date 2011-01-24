package battlegrid.game.execution;

import battlegrid.abstracts.Direction;
import battlegrid.abstracts.GameEntityInfo;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 16:54:27 <br/>
 */
abstract class GameEntity implements GameEntityInfo, Cloneable {
    public abstract GameEntity clone() throws CloneNotSupportedException;
    public abstract void setLife(int life);
    public abstract void setDirection(Direction direction);
    public abstract void setXY(int x, int y);
}
