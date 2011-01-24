package battlegrid.game.execution;

import battlegrid.abstracts.ActionHolder;
import battlegrid.abstracts.GameEntityType;
import battlegrid.abstracts.Player;
import battlegrid.game.GameView;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 15:59:11 <br/>
 */
public class Game implements Cloneable {
    private GameView view;
    private GameEntity[][] board;
    private  PlayerEntity[] playerEntities;

    public Game(GameEntityType[][] boardDescriptor, Player[] players, GameView view) {
        this.view = view;
        playerEntities = new PlayerEntity[players.length];
        board = new GameEntity[boardDescriptor.length][boardDescriptor[0].length];
        GameEntityFactory factory = new GameEntityFactory();

        int playerCounter = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                GameEntityType type = boardDescriptor[i][j];

                if(type.equals(GameEntityType.PLAYER)){
                    playerEntities[playerCounter] = new PlayerEntity(players[playerCounter], playerCounter);
                    board[i][j] = playerEntities[playerCounter];
                    playerCounter++;
                }
                else board[i][j] = factory.makeEntity(type);
            }
        }
    }

    // TODO: Handle copy of playerEntities
    public Game clone() throws CloneNotSupportedException {
        Game game = (Game) super.clone();
        game.view = view.clone();

        game.board = new GameEntity[board.length][board[0].length];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                game.board[i][j] = board[i][j].clone();
            }
        }
        return game;
    }

    public void startGame() {
        for(PlayerEntity entity : playerEntities){
            entity.getPlayer().init(board, entity);
        }
        view.init(board);

        while(nextRound());
    }

    private boolean nextRound() {
        ActionHolder[] actionHolders = collectActions();
        for(int i = 0; i < playerEntities.length; i++){
            actionHolders[i].getAction().execute(this, playerEntities[i]);
        }
        return false;
    }

    private ActionHolder[] collectActions() {
        ActionHolder[] actionHolders = new  ActionHolder[playerEntities.length];
        int i = 0;
        for(PlayerEntity entity : playerEntities) {
            actionHolders[i] = new ActionHolder() {
                private Action action = Action.NO_OP;

                public void setAction(Action action) {
                    if(action == null){
                        action = Action.NO_OP;
                    }
                    this.action = action;
                }

                public Action getAction() {
                    return action;
                }
            };

            entity.getPlayer().doAction(board, entity, actionHolders[i]);
            i++;
        }
        return actionHolders;
    }

    /**
     * Names: Itay Sabato, Rotem Barzilay <br/>
     * Logins: itays04, rotmus <br/>
     * IDs: 036910008, 300618592 <br/>
     * Date: 24/01/2011 <br/>
     * Time: 16:32:41 <br/>
     */
    public static enum Action {
        NO_OP {
            @Override
            public void execute(Game game, PlayerEntity playerEntity) {}
        },
        TURN_RIGHT {
            @Override
            public void execute(Game game, PlayerEntity playerEntity) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        },
        TURN_LEFT {
            @Override
            public void execute(Game game, PlayerEntity playerEntity) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        },
        MOVE_FWD {
            @Override
            public void execute(Game game, PlayerEntity playerEntity) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        },
        SHOOT {
            @Override
            public void execute(Game game, PlayerEntity playerEntity) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        public abstract void execute(Game game, PlayerEntity playerEntity);
    }
}
