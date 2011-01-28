package battlegrid.setup;

import battlegrid.abstracts.GameEntityType;
import battlegrid.abstracts.Player;
import battlegrid.game.execution.*;
import battlegrid.game.view.GameViewImpl;
import battlegrid.players.RandomPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Rotmus
 * Date: 26/01/2011
 * Time: 17:18:54
 * To change this template use File | Settings | File Templates.
 */
public class Setup {

    public static void main(String[] args) {
        GameEntityType[][] board = null;
        //reading level:
        File file = new File("resources\\properties\\level.txt");
        try {
            Scanner scanner = new Scanner(file);
            if(scanner.hasNext())  {
                board = new GameEntityType[Integer.parseInt(scanner.next())][Integer.parseInt(scanner.next())];
                scanner.nextLine();
            }
            int row = 0;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                for(int i = 0; i<line.length();i++){
                    switch(line.charAt(i)){
                        case('P'):board[row][i] = GameEntityType.PLAYER;break;
                        case('W'):board[row][i] = GameEntityType.WALL;break;
                        case('B'):board[row][i] = GameEntityType.BORDER;break;
                        case('N'):board[row][i] = GameEntityType.BLANK;break;
                    }
                }
                row++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //testing game GUI:
        Player[] players = new  Player[2];
        players[0] = new RandomPlayer();
        players[1] =  new RandomPlayer();
        GameViewImpl view = new GameViewImpl();
        Game game = new Game(view);
        game.init(board, players);
        System.out.println("The winner is player " + game.startGame());
        view.dispose();
    }
}
