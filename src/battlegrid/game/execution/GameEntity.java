package battlegrid.game.execution;

import battlegrid.abstracts.Direction;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.GameEntityType;
import battlegrid.setup.GameProperties;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 16:54:27 <br/>
 */
class GameEntity implements GameEntityInfo {
    private final long ID;
    private final GameEntityType TYPE;
    protected int life;
    protected Direction direction;
    protected int x;
    protected int y;

    public GameEntity(GameEntityType type, long id, int x, int y) {
        ID = id;
        TYPE = type;
        setXY(x,y);
        setLife(GameProperties.getGameProperties().getIntProperty(type.name()+".life"));
    }

    public GameEntity(GameEntityType type, long id, int x, int y, int life) {
        ID = id;
        TYPE = type;
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
        return false;
    }

    public boolean hasDirection() {
        return false;
    }

    public static void main(String[] args) {
        System.out.println(8 % 7);
    }
}