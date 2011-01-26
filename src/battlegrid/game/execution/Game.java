package battlegrid.game.execution;

import battlegrid.abstracts.*;
import battlegrid.game.GameView;
import battlegrid.setup.GameProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 15:59:11 <br/>
 */
public class Game {
    private GameView view;
    private GameEntityFactory factory;
    private GameEntity[][] board;
    private List<PlayerEntity> playerEntities;

    public Game(GameView view) {
        this.view = view;
    }

    public void init(GameEntityType[][] boardDescriptor, Player[] players) {
        playerEntities = new ArrayList<PlayerEntity>();
        board = new GameEntity[boardDescriptor.length][boardDescriptor[0].length];
        factory = new GameEntityFactory();

        int playerCounter = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                GameEntityType type = boardDescriptor[i][j];

                if(type.equals(GameEntityType.PLAYER)){
                    PlayerEntity playerEntity = new PlayerEntity(playerCounter, j, i, players[playerCounter]);
                    playerEntities.add(playerEntity);
                    board[i][j] =playerEntity;
                    playerCounter++;
                }
                else board[i][j] = factory.makeEntity(type, j, i);
            }
        }

        for(PlayerEntity entity : playerEntities){
            entity.getPlayer().init(board, entity);
        }
        view.init(board, playerEntities.toArray(new PlayerEntity[playerEntities.size()]));
    }

    public int startGame() {
        while(playerEntities.size() > 1) {
            nextRound();
        }
        return playerEntities.get(0).getID();
    }

    private void nextRound() {
        long startTime = System.currentTimeMillis();
        ActionHolder actionHolder = new ActionHolder() {
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

        for(PlayerEntity entity : playerEntities) {
            entity.getPlayer().doAction(board, entity, actionHolder);
            actionHolder.getAction().execute(this, entity);
            actionHolder.setAction(Action.NO_OP);
        }
        Iterator<PlayerEntity> iterator =  playerEntities.iterator();
        while(iterator.hasNext()){
            PlayerEntity entity = iterator.next();
            if(entity.getLife() == 0){
                iterator.remove();
            }
        }
        long roundTime = GameProperties.getIntProperty("round.time");
        long passedTime = System.currentTimeMillis() - startTime;
        
        if(passedTime < roundTime){
            try {
                Thread.sleep(roundTime - passedTime);
            } catch (InterruptedException e) {
                System.err.println("received interrupt");
            }
        }
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
            void execute(Game game, PlayerEntity playerEntity) {}
        },
        TURN_RIGHT {
            @Override
            void execute(Game game, PlayerEntity playerEntity) {
                int i = (playerEntity.getDirection().ordinal() + 1) % Direction.values().length;
                playerEntity.setDirection(Direction.values()[i]);
                game.view.updateMove(playerEntity.getX(),playerEntity.getY(),playerEntity.getX(),playerEntity.getY());
            }
        },
        TURN_LEFT {
            @Override
            void execute(Game game, PlayerEntity playerEntity) {
                int i = (playerEntity.getDirection().ordinal() - 1) % Direction.values().length;
                i = (Direction.values().length + i) % Direction.values().length;
                playerEntity.setDirection(Direction.values()[i]);
                game.view.updateMove(playerEntity.getX(),playerEntity.getY(),playerEntity.getX(),playerEntity.getY());
            }
        },
        MOVE_FWD {
            @Override
            void execute(Game game, PlayerEntity playerEntity) {
                int x = playerEntity.getX();
                int y =  playerEntity.getY();
                int nextX = x + playerEntity.getDirection().dx;
                int nextY = y + playerEntity.getDirection().dy;

                if(game.board[nextY][nextX].getType().equals(GameEntityType.BLANK)) {
                    game.board[nextY][nextX] =  playerEntity;
                    playerEntity.setXY(nextX,nextY);
                    game.board[y][x] = game.factory.makeEntity(GameEntityType.BLANK, x, y);
                    game.view.updateMove(x,y,nextX,nextY);
                }
            }
        },
        SHOOT {
            @Override
            void execute(Game game, PlayerEntity playerEntity) {
                int x = playerEntity.getX();
                int y = playerEntity.getY();
                do{
                    x += playerEntity.getDirection().dx;
                    y += playerEntity.getDirection().dy;
                } while(game.board[y][x].getType().equals(GameEntityType.BLANK));

                GameEntity wounded = game.board[y][x];
                if(wounded.getLife() != GameEntityInfo.IMMORTAL){
                    wounded.setLife(wounded.getLife() - 1);
                    if(wounded.getLife() == 0){
                        game.board[y][x] = game.factory.makeEntity(GameEntityType.BLANK, x, y);
                    }
                }

                game.view.updateShot(playerEntity.getX(),playerEntity.getY(),x,y);
            }
        };

        abstract void execute(Game game, PlayerEntity playerEntity);
    }
}
