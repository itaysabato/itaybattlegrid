package battlegrid.abstracts;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 15:23:13 <br/>
 */
public interface GameEntityInfo {
    int getID();
    GameEntityType getType();
    boolean canShoot();
    int getLife();
    boolean hasDirection();
    Direction getDirection();
    int getX();
    int getY();
}
