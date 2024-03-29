package battlegrid.players;

import battlegrid.abstracts.ActionHolder;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.Game;

import java.util.*;

import static battlegrid.setup.GameProperties.*;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 08/02/2011 <br/>
 * Time: 14:48:37 <br/>
 */
public class AStarPlayer implements Player {

    private int maxDepth;
    private StateFactory stateFactory;
    private List<State> closed;
    private Queue<State> active = new PriorityQueue<State>();

    public void setAttributes(Map<String, String> playerAttributes) {
        maxDepth = Integer.parseInt(playerAttributes.get("Player.maxExplore"));
    }

    public void init(GameEntityInfo[][] gameState, GameEntityInfo myState) {
        closed = new ArrayList<State>();
        stateFactory = new AStarStateFactory(closed);

    }

    public void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo) {
        active.clear();
        closed.clear();
        active.addAll(stateFactory.getInitialStates(gameState,myState));

        for(int i = 0; i < maxDepth; i++){
            State best = active.remove();
            if(best.getHeuristicValue() == 0){
                toDo.setAction(best.getRootAction());
                return;
            }
            if(!closed.contains(best)) {
                active.addAll(best.spawn());
                closed.add(best);
            }
        }
        toDo.setAction(active.remove().getRootAction());
    }

    public void gameOver(boolean youWin) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    interface State extends Comparable<State> {
        Game.Action getRootAction();
        List<State> spawn();
        int getHeuristicValue();
        int getPathValue();
    }

    interface StateFactory {
        List<State> getInitialStates(GameEntityInfo[][] gameState, GameEntityInfo myState);
    }
}
