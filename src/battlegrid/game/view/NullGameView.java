package battlegrid.game.view;

import battlegrid.abstracts.GameEntityInfo;
import battlegrid.game.GameView;
import battlegrid.game.execution.PlayerEntity;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 28/01/2011 <br/>
 * Time: 23:05:01 <br/>
 */
public class NullGameView implements GameView {
    public void init(GameEntityInfo[][] gameState, PlayerEntity[] playerEntities) {}
    public void updateShot(int shooterX, int shooterY, int woundedX, int woundedY) {}
    public void updateMove(int startX, int startY, int finishX, int finishY) {}
    public void dispose() {}
}
