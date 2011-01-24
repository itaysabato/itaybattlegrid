package battlegrid.abstracts;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 16:08:16 <br/>
 */
public interface Player {
    void init(GameEntityInfo[][] gameState, GameEntityInfo myState);
    void doAction(GameEntityInfo[][] gameState, GameEntityInfo myState, ActionHolder toDo);
}
