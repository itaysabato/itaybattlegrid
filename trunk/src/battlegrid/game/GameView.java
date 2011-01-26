package battlegrid.game;

import battlegrid.abstracts.GameEntityInfo;
import battlegrid.game.execution.PlayerEntity;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 16:42:38 <br/>
 */
public interface GameView extends Cloneable {
    void init(GameEntityInfo[][] gameState, PlayerEntity[] playerEntities);
    void updateShot(int shooterX, int shooterY, int woundedX, int woundedY);
    void updateMove(int startX, int startY, int finishX, int finishY);
}
