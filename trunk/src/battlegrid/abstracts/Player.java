package battlegrid.abstracts;

import java.util.Map;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 16:08:16 <br/>
 */
public interface Player {

    /**
     * Load user defined attributes.
     * Invoked once, after creation.
     */
    void setAttributes(Map<String, String> playerAttributes);

    /**
     * Invoked once in every game, before it runs, for initialization.
     * @param gameState  The state of the grid before the first player has his turn.
     * @param myState The initial state of this player.
     */
    void init(GameEntityInfo[][] gameState, GameEntityInfo myState);

    /**
     * Player must set toDo with an action before returning. Invoked once every round.
     * @param gameState The current state of the grid.
     * @param myState  The current state of this player.
     * @param toDo Holder for the next Action.
     */
    void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo);

    /**
     * Lets you know who won the game. Invoked at the end of each game.
     * @param youWin True if you won or false if you lost.
     */
    void gameOver(boolean youWin);
}
