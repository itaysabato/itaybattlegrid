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
        double reward = 0;
        if(current.isLockedOnHim == 1) reward += 1;
        if(current.isLockedOnMe == 1) reward -= 0.5;
        if(current.lifeDiff == QState.ADVANTAGE) reward += 0.75;
        if(current.quarter==current.myDirection) reward +=0.5;

        if(current.lifeDiff<previous.lifeDiff) reward += 0.25;
        if(current.isLockedOnHim>previous.isLockedOnHim) reward += 0.5;

        if(previous.isLockedOnHim==1 && lastAction== Game.Action.SHOOT)  reward += 0.5;

        return reward;
    }

    public double terminal(boolean youWin) {
        return youWin ? 9 : -2;
    }
}
