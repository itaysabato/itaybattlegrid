package battlegrid.game.execution;

import battlegrid.abstracts.Direction;
import battlegrid.abstracts.GameEntityType;
import battlegrid.abstracts.Player;
import battlegrid.setup.GameProperties;

import static battlegrid.setup.GameProperties.*;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 18:57:56 <br/>
 */
public class PlayerEntity extends GameEntity {
    private Player player;

    public PlayerEntity(long id, int x, int y, Player player) {
        super(GameEntityType.PLAYER, id, x, y,
                Integer.parseInt(getGameProperties().getPlayerAttribute((int) id,"Player.life")));
        setDirection(Direction.valueOf(getGameProperties().getPlayerAttribute((int) id,"Player.direction")));
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean canShoot() {
        return true;
    }

    @Override
    public boolean hasDirection() {
        return true;
    }
}
