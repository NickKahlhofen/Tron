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

    public Computer() {
        this.setBackground(ResourceTools.loadImageFromResource("Tron/tronbackground").getScaledInstance(1000, 700, Image.SCALE_SMOOTH));
        grid = new Grid(48, 31, 20, 20, new Point(20, 50), Color.BLUE);
        JB = new Tron(Direction.Left, Color.BLUE, grid);
        JB2 = new Tron(Direction.Down, Color.ORANGE, grid);

        barriers = new Barriers();
        barriers.add(new Barrier(10, 15, Color.gray, this, false));
        barriers.add(new Barrier(10, 15, Color.gray, this, false));
        barriers.add(new Barrier(10, 15, Color.gray, this, false));
        barriers.add(new Barrier(10, 15, Color.gray, this, false));
        barriers.add(new Barrier(10, 15, Color.gray, this, false));
        barriers.addBarrierRange(0, 0, 0, 35, Color.GRAY, this);

        items = new ArrayList<>();
        items.add(new Item(10, 5, "Power_UP",
                ResourceTools.loadImageFromResource("tron/Goldenapple.png"),
                this));
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
        // System.out.println("Come to me" + counter++);

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

    public void checkIntersections() {
        if (barriers != null) {

            for (Barrier barrier : barriers.getBarriers()) {
                if (barrier.getLocation().equals(JB.getHead())) {
                    // System.out.println("Game Over");
                    JB.addHealth(-1000);

                }

            }

            if (barriers.getBarriers().contains(JB.getHead())) {
                System.out.println("Game Over");
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        //System.out.println("key Press " + e. getKeyChar());
        // System.out.println("key Press " + e. getKeyCode());

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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            AudioPlayer.play("/Tron/apollo.wav");
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            AudioPlayer.play("/Tron/inpackcrash.wav");
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

}
