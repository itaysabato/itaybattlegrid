package battlegrid.players;

import battlegrid.abstracts.ActionHolder;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.Game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 15/02/2011 <br/>
 * Time: 21:47:54 <br/>
 */
public class QLearningPlayer implements Player {

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
        //TODO: init constants and rewards!
    }

    public void init(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        numEpisodes++;
        previous = null;
        lastAction = null;
    }

    public void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo) {
        QState current = new QState(gameState, myState);

        if(numEpisodes <= trainingDuration && previous != null) {
            double reward = rewarder.getReward(previous, lastAction, current);

            double[] Qs = Q.get(previous);
            if(Qs == null){
                Qs = new double[Game.Action.values().length];
                Q.put(previous, Qs);
            }

            int i = lastAction.ordinal();
            Qs[i] += alpha*(reward + gamma*getBestActionValue(current).second() - Qs[i] );
        }
        toDo.setAction(chooseAction(current));
    }

}
