/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tron;

import audio.AudioPlayer;
import audio.SoundManager;
import environment.ApplicationStarter;
import environment.Environment;
import grid.Grid;
import images.ResourceTools;
//import images.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
//import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Nick
 */
class Computer extends Environment implements CellDataProviderIntf {

    private Grid grid;
    private Tron JB_Orange;
    private Tron JB_Blue;
    private Barriers barriers;
    private ArrayList<Item> items;
    private GameState state = GameState.STOPPED;
    private MySoundManager soundManager;

    public Computer() {
        this.setBackground(ResourceTools.loadImageFromResource("Tron/tronbackground.jpg").getScaledInstance(1450, 800, Image.SCALE_SMOOTH));
        grid = new Grid(107, 54, 13, 13, new Point(20, 50), Color.BLUE);
//        JB = new Tron(new Point(1, grid.getRows() / 2), Direction.Left, Color.BLUE, grid);
//        JB2 = new Tron(new Point(grid.getColumns() - 2, grid.getRows() / 2), Direction.Down, Color.ORANGE, grid);

        resetGame();

        barriers = new Barriers();
        barriers.add(new Barrier(0, 05, Color.gray, this, false));

        barriers.addBarrierRange(0, 0, 0, 53, Color.GRAY, this);
        barriers.addBarrierRange(0, 53, 106, 53, Color.GRAY, this);
        barriers.addBarrierRange(106, 1, 106, 53, Color.GRAY, this);
        barriers.addBarrierRange(1, 0, 106, 0, Color.GRAY, this);
        items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add(new Item(randomInt(1, grid.getColumns()-1), randomInt(1, grid.getRows()-1), Item.POWER_UP_ORANGE, ResourceTools.loadImageFromResource("tron/Goldenapple.png"), this));        
        }
        
        for (int i = 0; i < 2; i++) {
            items.add(new Item(randomInt(1, grid.getColumns()), randomInt(1, grid.getRows()-1), Item.POWER_UP_BLUE, ResourceTools.loadImageFromResource("tron/PotionSwift.png"), this));        
        }
        
        for (int i = 0; i < 1; i++) {
            items.add(new Item(randomInt(1, grid.getColumns()), randomInt(1, grid.getRows()-1), Item.POWER_UP_INVULNERABLE, ResourceTools.loadImageFromResource("tron/PotionSwift.png"), this));        
        }
                for (int i = 0; i < 1; i++) {
            items.add(new Item(randomInt(1, grid.getColumns()), randomInt(1, grid.getRows()-1), Item.POWER_UP_MAGIC, ResourceTools.loadImageFromResource("tron/Book.png"), this));        
        }

        
        state = GameState.RUNNING;
        
    }
    
    //soundManager = SoundManager.getSoundManager();
    
    public int randomInt(int min, int max){
        return (int) (min + (Math.random() * (max - min)));
    }
        

    @Override
    public void initializeEnvironment() {

    }
    
    int moveDelay_Orange = 0;
    int moveDelayLimit_Orange = 3;

    int moveDelay_Blue = 0;
    int moveDelayLimit_Blue = 3;

    @Override
    public void timerTaskHandler() {
        if (state == GameState.RUNNING) {

            if (JB_Orange != null) {
                if (moveDelay_Orange >= moveDelayLimit_Orange) {
                    moveDelay_Orange = 0;
                    JB_Orange.move();
                } else {
                    moveDelay_Orange++;
                }
            }

            if (JB_Blue != null) {
                if (moveDelay_Blue >= moveDelayLimit_Blue) {
                    moveDelay_Blue = 0;
                    JB_Blue.move();
                } else {
                    moveDelay_Blue++;
                }
            }

            checkIntersections();
        }
    }

    public void checkIntersections() {
        if (barriers != null) {
            for (Barrier barrier : barriers.getBarriers()) {
//                state = GameState.STOPPED;
                if (barrier.getLocation().equals(JB_Orange.getHead())) {
                    JB_Orange.addHealth(-1000);
                    System.out.println("Game Over");
                    resetGame();
                }

                if (barrier.getLocation().equals(JB_Blue.getHead())) {
                    JB_Blue.addHealth(-1000);
                    System.out.println("Game Over");
                    resetGame();
                }

            }
        }

        //check if the snakes have hit themselves
        if ((JB_Orange != null) && JB_Orange.selfHit()) {
            System.out.println("JB touched himself inappropriately");
            state = GameState.CRASHED;
        }

        if ((JB_Blue != null) && JB_Blue.selfHit()) {
            System.out.println("JB2 touched himself inappropriately");
            state = GameState.CRASHED;
        }

        if ((JB_Orange != null) && (JB_Blue != null)) {
            if (JB_Orange.doesPointCollide(JB_Blue.getHead())) {
                System.out.println("JB2 has crashed into JB");
                state = GameState.CRASHED;
            }

            if (JB_Blue.doesPointCollide(JB_Orange.getHead())) {
                System.out.println("JB has crashed into JB");
                state = GameState.CRASHED;
            }
        }

        //now, check the items
        if (items != null) {
            for (Item item : items) {
                if (JB_Orange.getHead().equals(item.getLocation())) {
                    System.out.println("woooooooooot");
                    //change position of the item that got hit;
                    item.setX(randomInt(1, grid.getColumns()-1));
                    item.setY(randomInt(1, grid.getRows()-1));

                    //write the rules!!!!
                    // if `PU Blue' and JB_Blue, then speed up!
                    // if `PU Orange' and JB_Blue, then slow down!
                    
                    // if `PU Blue' and JB_Orange, then slow down!
                    // if `PU Orange' and JB_Orange, then speed up!
                    
                    if (item.getType().equals(Item.POWER_UP_BLUE)) {
                        moveDelayLimit_Orange++;
                    } else if (item.getType().equals(Item.POWER_UP_ORANGE)) {
                        moveDelayLimit_Orange--;
                    } else if (item.getType().equals(Item.POWER_UP_MAGIC)) {
                        JB_Orange.removeTail();
                    }
                }
                
                
                if (JB_Blue.getHead().equals(item.getLocation())) {
                    System.out.println("woooooooooot");
                    //change position of the item that got hit;
                    item.setX(randomInt(1, grid.getColumns()-1));
                    item.setY(randomInt(1, grid.getRows()-1));

                    //write the rules!!!!
                    // if `PU Blue' and JB_Blue, then speed up!
                    // if `PU Orange' and JB_Blue, then slow down!
                    
                    // if `PU Blue' and JB_Orange, then slow down!
                    // if `PU Orange' and JB_Orange, then speed up!
                    
                    if (item.getType().equals(Item.POWER_UP_BLUE)) {
                        moveDelayLimit_Blue--;
                    } else if (item.getType().equals(Item.POWER_UP_ORANGE)) {
                        moveDelayLimit_Blue++;    
                    } else if (item.getType().equals(Item.POWER_UP_MAGIC)) {
                        JB_Blue.removeTail();
                    }
                }
                
                
                
                
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        //System.out.println("key Press " + e. getKeyChar());
        // System.out.println("key Press " + e. getKeyCode());
        if (state == GameState.RUNNING) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                JB_Blue.setDirection(Direction.Up);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                JB_Blue.setDirection(Direction.Right);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                JB_Blue.setDirection(Direction.Down);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                JB_Blue.setDirection(Direction.Left);
            }

            if (e.getKeyCode() == KeyEvent.VK_W) {
                JB_Orange.setDirection(Direction.Up);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                JB_Orange.setDirection(Direction.Right);
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                JB_Orange.setDirection(Direction.Down);
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                JB_Orange.setDirection(Direction.Left);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            AudioPlayer.play("/tron/apollo.wav");
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
//            AudioPlayer.play("/tron/inpackcrash.wav");
            //AudioPlayer.play("/tron/starwars.wav");
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (state == GameState.RUNNING) {
                state = GameState.STOPPED;
            } else if (state == GameState.STOPPED) {
                state = GameState.RUNNING;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_N) {
            if ((state == GameState.CRASHED)) {
                resetGame();
            }
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (grid != null) {
            grid.paintComponent(graphics);
        }
        if (JB_Orange != null) {
            JB_Orange.draw(graphics);

        }
        if (JB_Blue != null) {
            JB_Blue.draw(graphics);

        }

        if (barriers != null) {
            barriers.draw(graphics);
//            for (int i = 0; i < barriers.size(); i++) {
//                barriers.get(i).draw(graphics);
//            }
        }
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                items.get(i).draw(graphics);
            }
        }
        
        graphics.setColor(Color.BLUE);
        graphics.setFont(new Font("Calibri", Font.ITALIC, 35));
        graphics.drawString("Player 1: Stark", 1100, 40);
        
        graphics.setColor(Color.ORANGE);
        graphics.setFont(new Font("Calibri", Font.ITALIC, 35));
        graphics.drawString("Player 2: Flash", 100, 40);
        
       
    }

    @Override
    public int getCellWidth() {
        return grid.getCellWidth();
    }

    @Override
    public int getCellhight() {
        return grid.getCellHeight();
    }

    @Override
    public int getSystemCoordX(int x, int y) {
        return grid.getCellSystemCoordinate(x, y).x;
    }

    @Override
    public int getSystemCoordY(int x, int y) {
        return grid.getCellSystemCoordinate(x, y).y;
    }

    private void resetGame() {
//        JB = new Tron(Direction.Left, Color.BLUE, grid);
//        JB2 = new Tron(Direction.Down, Color.ORANGE, grid);
        JB_Orange = new Tron(new Point(10, grid.getRows() / 2), Direction.Right, Color.ORANGE, grid);
        JB_Blue = new Tron(new Point(grid.getColumns() - 10, grid.getRows() / 2), Direction.Left, Color.BLUE, grid);
        state = GameState.STOPPED;
    }
}
