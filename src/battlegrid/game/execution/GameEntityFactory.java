package battlegrid.game.execution;

import battlegrid.abstracts.GameEntityType;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 18:43:57 <br/>
 */
public class GameEntityFactory {
    private int[] IDs = new int[GameEntityType.values().length] ;
    
    public GameEntity makeEntity(GameEntityType type, int x, int y) {
        switch (type) {
            case PLAYER:throw new RuntimeException("make entity is not " +
                    "supposed to make a player entity!");
            default: return new GameEntity(type, IDs[type.ordinal()]++, x, y);
        }
    }
}
