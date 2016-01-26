/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tron;

import audio.AudioPlayer;
import environment.Environment;
import grid.Grid;
import images.ResourceTools;
//import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
//import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Nick
 */
class Computer extends Environment implements CellDataProviderIntf {

    private Grid grid;
    private Tron JB;
    private Tron JB2;
    private Barriers barriers;
    private ArrayList<Item> items;
    private GameState state = GameState.STOPPED;

    public Computer() {
        this.setBackground(ResourceTools.loadImageFromResource("Tron/tronbackground.jpg").getScaledInstance(1000, 700, Image.SCALE_SMOOTH));
        grid = new Grid(48, 31, 20, 20, new Point(20, 50), Color.BLUE);
//        JB = new Tron(new Point(1, grid.getRows() / 2), Direction.Left, Color.BLUE, grid);
//        JB2 = new Tron(new Point(grid.getColumns() - 2, grid.getRows() / 2), Direction.Down, Color.ORANGE, grid);

        resetGame();

        barriers = new Barriers();
        barriers.add(new Barrier(0, 05, Color.gray, this, false));

        barriers.addBarrierRange(0, 0, 0, 30, Color.GRAY, this);
        barriers.addBarrierRange(0, 0, 47, 0, Color.GRAY, this);
        barriers.addBarrierRange(1, 30, 47, 30, Color.GRAY, this);
        barriers.addBarrierRange(47, 1, 47, 30, Color.GRAY, this);

        items = new ArrayList<>();
        items.add(new Item(10, 5, "Power_UP", ResourceTools.loadImageFromResource("tron/Goldenapple.png"), this));
        items.add(new Item(5, 22, "Power_UP", ResourceTools.loadImageFromResource("tron/Goldenapple.png"), this));
        items.add(new Item(14, 3, "Power_UP", ResourceTools.loadImageFromResource("tron/Goldenapple.png"), this));
        items.add(new Item(24, 3, "Invisible", ResourceTools.loadImageFromResource("tron/PotionSwift.png"), this));

        state = GameState.RUNNING;
    }

    @Override
    public void initializeEnvironment() {

    }
    int moveDelay = 0;
    int moveDelayLimit = 3;

    int moveDelay2 = 0;
    int moveDelayLimit2 = 3;

    @Override
    public void timerTaskHandler() {
        if (state == GameState.RUNNING) {

            if (JB != null) {
                if (moveDelay >= moveDelayLimit) {
                    moveDelay = 0;
                    JB.move();
                } else {
                    moveDelay++;
                }
            }

            if (JB2 != null) {
                if (moveDelay2 >= moveDelayLimit2) {
                    moveDelay2 = 0;
                    JB2.move();
                } else {
                    moveDelay2++;
                }

            }
            checkIntersections();
        }
    }

    public void checkIntersections() {
        if (barriers != null) {
            for (Barrier barrier : barriers.getBarriers()) {

//                state = GameState.STOPPED;
                if (barrier.getLocation().equals(JB.getHead())) {
                    JB.addHealth(-1000);
                    System.out.println("Game Over");
                    resetGame();
                }

                if (barrier.getLocation().equals(JB2.getHead())) {
                    JB2.addHealth(-1000);
                    System.out.println("Game Over");
                    resetGame();
                }

            }
        }

        //check if the snakes have hit themselves
        if ((JB != null) && JB.selfHit()) {
            System.out.println("JB touched himself inappropriately");
            state = GameState.CRASHED;
        }

        if ((JB2 != null) && JB2.selfHit()) {
            System.out.println("JB2 touched himself inappropriately");
            state = GameState.CRASHED;
        }

        if ((JB != null) && (JB2 != null)) {
            if (JB.doesPointCollide(JB2.getHead())) {
                System.out.println("JB2 has crashed into JB");
                state = GameState.CRASHED;
            }

            if (JB2.doesPointCollide(JB.getHead())) {
                System.out.println("JB has crashed into JB");
                state = GameState.CRASHED;
            }
        }

        //now, check the items
        if (items != null) {
            for (Item item : items) {
                if (JB.getHead().equals(item.getLocation())) {
                    System.out.println("woooooooooot");
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
                JB.setDirection(Direction.Up);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                JB.setDirection(Direction.Right);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                JB.setDirection(Direction.Down);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                JB.setDirection(Direction.Left);
            }

            if (e.getKeyCode() == KeyEvent.VK_W) {
                JB2.setDirection(Direction.Up);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                JB2.setDirection(Direction.Right);
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                JB2.setDirection(Direction.Down);
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                JB2.setDirection(Direction.Left);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            AudioPlayer.play("/Tron/apollo.wav");
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            AudioPlayer.play("/Tron/inpackcrash.wav");
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
        if (JB != null) {
            JB.draw(graphics);

        }
        if (JB2 != null) {
            JB2.draw(graphics);

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
        JB = new Tron(new Point(1, grid.getRows() / 2), Direction.Right, Color.ORANGE, grid);
        JB2 = new Tron(new Point(grid.getColumns() - 2, grid.getRows() / 2), Direction.Left, Color.BLUE, grid);
        state = GameState.STOPPED;
    }
}
