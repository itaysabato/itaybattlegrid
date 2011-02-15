package battlegrid.players;

import battlegrid.game.execution.Game;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 15/02/2011 <br/>
 * Time: 23:49:24 <br/>
 */
public interface Rewarder {
    double getReward(QState previous, Game.Action lastAction, QState current);
    double terminal(boolean youWin);
}
