package battlegrid.abstracts;

import battlegrid.game.execution.Game;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 16:31:47 <br/>
 */
public interface ActionHolder {
    void setAction(Game.Action action);
    Game.Action getAction();
}
