package battlegrid.game.execution;

import battlegrid.abstracts.Direction;
import battlegrid.abstracts.GameEntityType;
import battlegrid.abstracts.Player;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 18:57:56 <br/>
 */
public class PlayerEntity extends GameEntity {
    public PlayerEntity(Player player, int playerID) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public Player getPlayer() {
        return null;
    }

    @Override
    public GameEntity clone() throws CloneNotSupportedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLife(int life) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDirection(Direction direction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setXY(int x, int y) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getID() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public GameEntityType getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean canShoot() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getLife() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasDirection() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Direction getDirection() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getX() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getY() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
