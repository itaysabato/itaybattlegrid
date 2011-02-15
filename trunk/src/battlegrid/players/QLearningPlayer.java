package battlegrid.players;

import battlegrid.abstracts.ActionHolder;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.Game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 15/02/2011 <br/>
 * Time: 21:47:54 <br/>
 */
public class QLearningPlayer implements Player {

    private final Random random = new Random();
    private double alpha;
    private double gamma;
    private double epsilon;
    private int numEpisodes = 0;
    private int trainingDuration;
    private QState previous;
    private Game.Action lastAction;
    private Rewarder rewarder;
    private Map<QState, double[]> Q = new HashMap<QState,double[]>();

    public void setAttributes(Map<String, String> playerAttributes) {
        alpha = Integer.parseInt(playerAttributes.get("Player.alpha"));
        gamma = Integer.parseInt(playerAttributes.get("Player.gamma"));
        epsilon = Integer.parseInt(playerAttributes.get("Player.epsilon"));
        trainingDuration = Integer.parseInt(playerAttributes.get("Player.trainingDuration"));
        rewarder = new QRewarder();
    }

    public void init(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        numEpisodes++;
        previous = null;
        lastAction = null;
    }

    public void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo) {
        QState current = new QState(gameState, myState);

        if(numEpisodes <= trainingDuration && previous != null) {
            updateQ(rewarder.getReward(previous, lastAction, current), getBestPair(current)[1]);
        }
        toDo.setAction(chooseAction(current));
    }

    private void updateQ(double reward, double nextReward) {
        double[] Qs = Q.get(previous);
        if(Qs == null){
            Qs = new double[Game.Action.values().length];
            Q.put(previous, Qs);
        }

        int i = lastAction.ordinal();
        Qs[i] += alpha*(reward + gamma*nextReward - Qs[i] );
    }

    private Game.Action chooseAction(QState current) {
        int i;
        if(random.nextDouble() < epsilon){
            i = random.nextInt(Game.Action.values().length);
        }
        else {
            i = (int) getBestPair(current)[0];
        }
        return Game.Action.values()[i];
    }

    private double[] getBestPair(QState current) {
        double[] Qs = Q.get(current);
        if(Qs == null){
            Qs = new double[Game.Action.values().length];
            Q.put(current, Qs);
        }

        double bestValue = 0;
        double bestAction = 0;
        for(int i = 0; i < Qs.length; i++){
            if(bestValue < Qs[i]) {
                bestValue =  Qs[i];
                bestAction = i;
            }
        }
        return new double[]{bestAction, bestValue};
    }

    public void gameOver(boolean youWin) {
        updateQ(rewarder.terminal(youWin), rewarder.terminal(youWin));               
    }

}
