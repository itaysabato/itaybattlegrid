package battlegrid.game.view;

import battlegrid.abstracts.Direction;
import battlegrid.abstracts.GameEntityInfo;
import battlegrid.abstracts.GameEntityType;
import battlegrid.game.GameView;
import battlegrid.game.execution.PlayerEntity;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;

import static battlegrid.setup.GameProperties.*;
import static java.lang.Thread.sleep;

/**
 * Created by IntelliJ IDEA.
 * User: Rotmus
 * Date: 23/01/2011
 * Time: 18:03:01
 * To change this template use File | Settings | File Templates.
 */
public class GameViewImpl implements GameView {


    private JFrame frame;
    private JPanel[][] cells;
    private GameEntityInfo[][] gameState;
    private PlayerEntity[] playerEntities;
    private JLabel[] playersHealth;
    private int[] scores;

    public GameViewImpl(int[] scores){
        this.scores = scores;
        frame = new JFrame("BATTLE GRID");
    }

    private void createAndShowGUI() {
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        create(frame);
        frame.pack();
    }

    private void create(JFrame frame){
        //creates panels and organize them in the window:
        JPanel window = new JPanel();
        window.setLayout(new BoxLayout(window,BoxLayout.Y_AXIS));
        frame.getContentPane().add(window);
        JPanel top= new JPanel();
        JPanel topRight = new JPanel();
        JPanel topLeft= new JPanel();
        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        topRight.setLayout(new BoxLayout(topRight,BoxLayout.Y_AXIS));
        topLeft.setLayout(new BoxLayout(topLeft,BoxLayout.Y_AXIS));
        window.add(top);
        top.add(topLeft);
        top.add(topRight);
        JPanel grid = new JPanel();
        GridLayout g = new GridLayout(cells.length, cells[0].length);
        grid.setLayout(g);
        window.add(grid);
        TitledBorder topLeftTitle = new TitledBorder("Players Info");
        topLeft.setBorder(topLeftTitle);

        //setting info labels:
        for(int i = 0;i<playerEntities.length;i++) {
            JPanel current = new JPanel();
            current.setLayout(new BoxLayout(current,BoxLayout.X_AXIS));
            topLeft.add(current);

            playersHealth[i] = new JLabel("health - "+playerEntities[i].getLife()+"  |  ") ;
            current.add(new JLabel("name - "+getGameProperties().getPlayerAttribute(i, "Player.name")+"  |  "));
            current.add(new JLabel("type - "+getGameProperties().getPlayerAttribute(i, "Player.className")+"  |  "));
            current.add(new JLabel("color - "+getGameProperties().getPlayerAttribute(i, "Player.color")+"  | "));
            current.add(playersHealth[i]);
            current.add(new JLabel("wins - "+scores[i]));
        }
        for(Component comp:topLeft.getComponents()){
            JComponent comp1 =  (JComponent)comp;
            comp1.setAlignmentX(Component.LEFT_ALIGNMENT);
        }


        //setting grid icons:
        for(int i = 0;i< cells.length;i++){
            for(int j = 0;j< cells[0].length;j++)   {
                grid.add(cells[i][j]);
            }
        }
    }

    private void updateInfo() {
        for(int i = 0;i<playerEntities.length;i++) {
            playersHealth[i].setText("health - "+playerEntities[i].getLife()+"  |  ") ;
        }
    }

    public void init(GameEntityInfo[][] gameState, PlayerEntity[] playerEntities) {
        frame.dispose();
        frame = new JFrame("BATTLE GRID");
        this.gameState = gameState;
        this.playerEntities = playerEntities;
        playersHealth = new JLabel[playerEntities.length];
        int counter = 0;
        cells = new JPanel[gameState.length][gameState[0].length];
        for(int i = 0;i< gameState.length;i++){
            for(int j = 0;j< gameState[i].length;j++)  {
                JPanel panel = new JPanel();
                JLabel label;
                cells[i][j] = panel;
                panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
                panel.setPreferredSize(new Dimension(getGameProperties().getIntProperty("Square.width"),getGameProperties().getIntProperty("Square.height")));
                if(gameState[i][j].getType().equals(GameEntityType.PLAYER)) {
                    panel.setBackground(Coloring.valueOf(getGameProperties().getPlayerAttribute(counter,"Player.color")).color);
                    label =  new JLabel(getGameProperties().getImage("Direction."+getGameProperties().getPlayerAttribute(counter,"Player.direction")));
                    counter++;
                }
                else {
                    label =  new JLabel(getGameProperties().getImage(gameState[i][j].getType()+".image"));
                }
                panel.add(label);
                for(Component comp:panel.getComponents()){
                    JComponent comp1 =  (JComponent)comp;
                    comp1.setAlignmentX(Component.CENTER_ALIGNMENT);
                }
            }
        }
        createAndShowGUI();
    }

    public void updateShot(int shooterX, int shooterY, int woundedX, int woundedY) {
        long shotInterval = getGameProperties().getIntProperty("Shot.time");
        int addX = 0,addY = 0;
        if(shooterX<woundedX)  addX = 1;
        else if(shooterX>woundedX) addX = -1;
        else addX = 0;
        if(shooterY<woundedY)  addY = 1;
        else if(shooterY>woundedY) addY = -1;
        else addY = 0;
        //print shot:
        shooterX += addX;
        shooterY += addY;
        int startX = shooterX, startY = shooterY;
        try {
            while(!(shooterY==woundedY && shooterX==woundedX)){
                JLabel cell = (JLabel)cells[shooterY][shooterX].getComponents()[0];
                cell.setIcon(getGameProperties().getImage("Shot.image"));
                sleep(shotInterval);
                shooterX += addX;
                shooterY += addY;
            }
            //print hit:
            JLabel hitCell = (JLabel)cells[woundedY][woundedX].getComponents()[0];
            ImageIcon beforeHit = (ImageIcon)hitCell.getIcon();
            hitCell.setIcon(getGameProperties().getImage("Hit.image"));
            sleep(shotInterval*2);
            hitCell.setIcon(beforeHit);
            sleep(shotInterval);
            hitCell.setIcon(getGameProperties().getImage("Hit.image"));
            sleep(shotInterval*2);
            hitCell.setIcon(beforeHit);
            //remove shot:
            while(!(startY==woundedY && startX==woundedX)){
                JLabel cell = (JLabel)cells[startY][startX].getComponents()[0];
                cell.setIcon(getGameProperties().getImage("BLANK.image"));
                startX += addX;
                startY += addY;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(gameState[woundedY][woundedX].getType().equals(GameEntityType.WALL)){
            JLabel cell = (JLabel)cells[woundedY][woundedX].getComponents()[0];
            cell.setIcon(getGameProperties().getImage("BROKEN_WALL.image"));
        }
        //remove dead target
        if(gameState[woundedY][woundedX].getType().equals(GameEntityType.BLANK)){
            JLabel cell = (JLabel)cells[woundedY][woundedX].getComponents()[0];
            cell.setIcon(getGameProperties().getImage("BLANK.image"));
        }
        updateInfo();
    }

    public void updateMove(int startX, int startY, int finishX, int finishY) {
        //rotate:
        if(startX==finishX && startY==finishY) {
            JLabel cell = (JLabel)cells[startY][startX].getComponents()[0];
            cell.setIcon(getGameProperties().getImage("Direction."+gameState[startY][startX].getDirection()));
        }
        //forward:
        else{
            JLabel cell = (JLabel)cells[startY][startX].getComponents()[0];
            cell.setIcon(getGameProperties().getImage("BLANK.image"));
            JLabel cell2 = (JLabel)cells[finishY][finishX].getComponents()[0];
            cell2.setIcon(getGameProperties().getImage("Direction."+gameState[finishY][finishX].getDirection()));
            int i = (int) gameState[finishY][finishX].getID();
            cells[finishY][finishX].setBackground(Coloring.valueOf(getGameProperties().getPlayerAttribute(i,"Player.color")).color);
        }
    }

    public void dispose() {
        frame.dispose();
    }
}


