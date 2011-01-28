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

import static java.lang.Thread.sleep;

/**
 * Created by IntelliJ IDEA.
 * User: Rotmus
 * Date: 23/01/2011
 * Time: 18:03:01
 * To change this template use File | Settings | File Templates.
 */
public class GameGUI implements GameView {
    private static final int HIEGHT = 50;
    private static final int WIDTH = 50;
    private static final int INFO_SIZE = 3;
    private static final int PLAYER_ID = 0;
    private static final int PLAYER_HEALTH = 1;
    private static final int PLAYER_COLOR = 2;
    private static final long SHOT_INTERVAL = 30;

    private static final ImageIcon BORDER = new ImageIcon("resources\\images\\border.png");
    private static final ImageIcon WALL = new ImageIcon("resources\\images\\wall.png");
    private static final ImageIcon BLANK = new ImageIcon("resources\\images\\bg.png");
    private static final ImageIcon PLAYER = new ImageIcon("resources\\images\\up.png");
    private static final ImageIcon SHOT = new ImageIcon("resources\\images\\shot.png");
    private static final ImageIcon HIT = new ImageIcon("resources\\images\\hit.png");

    private static final ImageIcon NORTH = new ImageIcon("resources\\images\\up.png");
    private static final ImageIcon NORTH_EAST = new ImageIcon("resources\\images\\upRight.png");
    private static final ImageIcon EAST = new ImageIcon("resources\\images\\right.png");
    private static final ImageIcon SOUTH_EAST = new ImageIcon("resources\\images\\downRight.png");
    private static final ImageIcon SOUTH = new ImageIcon("resources\\images\\down.png");
    private static final ImageIcon SOUTH_WEST = new ImageIcon("resources\\images\\downLeft.png");
    private static final ImageIcon WEST = new ImageIcon("resources\\images\\left.png");
    private static final ImageIcon NORTH_WEST = new ImageIcon("resources\\images\\upLeft.png");

    static private Map<GameEntityType,ImageIcon> imageMap = new HashMap<GameEntityType,ImageIcon>();
    static private Map<Direction,ImageIcon> dircetionMap = new HashMap<Direction,ImageIcon>();
    static private Color[] playersColors = {Color.red,Color.blue,Color.yellow,Color.green,Color.gray,Color.black};
    static private String[] colors = {"red","blue","yellow","green","gray","black"};

    private JFrame frame;
    private JPanel[][] cells;
    private GameEntityInfo[][] gameState;
    private PlayerEntity[] playerEntities;
    private JLabel[][] playersInfo;

    public GameGUI(){
        frame = new JFrame("BATTLE GRID");
    }

    public static void InitializeMaps() {
        imageMap.put(GameEntityType.BORDER,BORDER);
        imageMap.put(GameEntityType.BLANK,BLANK);
        imageMap.put(GameEntityType.PLAYER,PLAYER);
        imageMap.put(GameEntityType.WALL,WALL);
        dircetionMap.put(Direction.NORTH,NORTH);
        dircetionMap.put(Direction.NORTH_EAST,NORTH_EAST);
        dircetionMap.put(Direction.EAST,EAST);
        dircetionMap.put(Direction.SOUTH_EAST,SOUTH_EAST);
        dircetionMap.put(Direction.SOUTH,SOUTH);
        dircetionMap.put(Direction.SOUTH_WEST,SOUTH_WEST);
        dircetionMap.put(Direction.WEST,WEST);
        dircetionMap.put(Direction.NORTH_WEST,NORTH_WEST);
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
        topLeft.setLayout(new GridLayout(playerEntities.length, 0));
        window.add(top);
        top.add(topLeft);
        top.add(topRight);
        JPanel grid = new JPanel();
        GridLayout g = new GridLayout(cells.length, cells[0].length);
        grid.setLayout(g);
        window.add(grid);
        TitledBorder topLeftTitle = new TitledBorder("Players Info");
        topLeft.setBorder(topLeftTitle);
        TitledBorder topRightTitle = new TitledBorder("Setting");
        topRight.setBorder(topRightTitle);
        TitledBorder gridTitle = new TitledBorder("Game");
        grid.setBorder(gridTitle);
        topRight.add(new JButton("Back to setup"));
        topRight.add(new JButton("Something"));
        for(Component comp:topRight.getComponents()){
            JComponent comp1 =  (JComponent)comp;
            comp1.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        //setting info labels:
        for(int i = 0;i<playerEntities.length;i++) {
            playersInfo[i][PLAYER_ID] = new JLabel("player: "+playerEntities[i].getID());
            playersInfo[i][PLAYER_HEALTH] = new JLabel("health: "+playerEntities[i].getLife()) ;
            playersInfo[i][PLAYER_COLOR] = new JLabel("color: "+colors[i]) ;
            topLeft.add(playersInfo[i][PLAYER_ID]);
            topLeft.add(playersInfo[i][PLAYER_HEALTH]);
            topLeft.add(playersInfo[i][PLAYER_COLOR]);
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
            playersInfo[i][PLAYER_ID].setText("player: "+playerEntities[i].getID());
            playersInfo[i][PLAYER_HEALTH].setText("health: "+playerEntities[i].getLife()) ;
            playersInfo[i][PLAYER_COLOR].setText("color: "+colors[i]) ;
        }
    }

    public void init(GameEntityInfo[][] gameState, PlayerEntity[] playerEntities) {
        frame.dispose();
        frame = new JFrame("BATTLE GRID");
        this.gameState = gameState;
        this.playerEntities = playerEntities;
        playersInfo = new JLabel[playerEntities.length][INFO_SIZE];
        int counter = 0;
        cells = new JPanel[gameState.length][gameState[0].length];
        for(int i = 0;i< gameState.length;i++){
            for(int j = 0;j< gameState[i].length;j++)   {
                JLabel label =  new JLabel(imageMap.get(gameState[i][j].getType()));
                JPanel panel = new JPanel();
                cells[i][j] = panel;
                panel.add(label);
                panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
                panel.setPreferredSize(new Dimension(WIDTH,HIEGHT));
                if(gameState[i][j].getType().equals(GameEntityType.PLAYER)) {
                    panel.setBackground(playersColors[counter]);
                    counter++;
                }
            }
        }                
        createAndShowGUI();
    }

    public void updateShot(int shooterX, int shooterY, int woundedX, int woundedY) {
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
                cell.setIcon(SHOT);
                sleep(SHOT_INTERVAL);
                shooterX += addX;
                shooterY += addY;
            }
            //print hit:
            JLabel hitCell = (JLabel)cells[woundedY][woundedX].getComponents()[0];
            ImageIcon beforeHit = (ImageIcon)hitCell.getIcon();
            hitCell.setIcon(HIT);
            sleep(SHOT_INTERVAL*2);
            hitCell.setIcon(beforeHit);
            sleep(SHOT_INTERVAL);
            hitCell.setIcon(HIT);
            sleep(SHOT_INTERVAL*2);
            hitCell.setIcon(beforeHit);
            //remove shot:
            while(!(startY==woundedY && startX==woundedX)){
                JLabel cell = (JLabel)cells[startY][startX].getComponents()[0];
                cell.setIcon(BLANK);
                startX += addX;
                startY += addY;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //remove dead target
        if(gameState[woundedY][woundedX].getType().equals(GameEntityType.BLANK)){
            JLabel cell = (JLabel)cells[woundedY][woundedX].getComponents()[0];
            cell.setIcon(BLANK);
        }
        updateInfo();
    }

    public void updateMove(int startX, int startY, int finishX, int finishY) {
        //rotate:
        if(startX==finishX && startY==finishY) {
            JLabel cell = (JLabel)cells[startY][startX].getComponents()[0];
            cell.setIcon(dircetionMap.get(gameState[startY][startX].getDirection()));
        }
        //forward:
        else{
            JLabel cell = (JLabel)cells[startY][startX].getComponents()[0];
            cell.setIcon(BLANK);
            JLabel cell2 = (JLabel)cells[finishY][finishX].getComponents()[0];
            cell2.setIcon(dircetionMap.get(gameState[finishY][finishX].getDirection()));
            cells[finishY][finishX].setBackground(playersColors[(int)gameState[finishY][finishX].getID()]);
        }
    }

    public void dispose() {
        frame.dispose();
    }
}


