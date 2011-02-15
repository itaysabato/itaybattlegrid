package battlegrid.players;

import battlegrid.game.execution.Game;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 16/02/2011 <br/>
 * Time: 00:50:52 <br/>
 */
public class QRewarder implements Rewarder {
    
    public double getReward(QState previous, Game.Action lastAction, QState current) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double terminal(boolean youWin) {
        return youWin ? 1 : -1;
    }
}
