/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tron;

import environment.Environment;
import grid.Grid;
//import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
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
    private ArrayList<Barrier> myBarriers;

    public Computer() {
        grid = new Grid(55, 30, 20, 20, new Point(20, 50), Color.BLACK);
        JB = new Tron(Direction.Left, grid);

        myBarriers = new ArrayList<>(); 
        myBarriers.add(new Barrier(10, 15, Color.gray, this, false));
        myBarriers.add(new Barrier(10, 15, Color.gray, this, false));
        myBarriers.add(new Barrier(10, 15, Color.gray, this, false));
        myBarriers.add(new Barrier(10, 15, Color.gray, this, false));
        myBarriers.add(new Barrier(10, 15, Color.gray, this, false));
        
    }

    @Override
    public void initializeEnvironment() {

    }
    int moveDelay = 0;
    int moveDelayLimit = 3;
  

    @Override
    public void timerTaskHandler() {
       // System.out.println("Come to me" + counter++);

        if (JB != null) {
           if (moveDelay >= moveDelayLimit){
               moveDelay = 0;
               JB.move();
           } else {
                moveDelay++;
           }
            
          
            checkIntersections();
        }
    }
    
    public void checkIntersections(){
        if (myBarriers.contains(JB.getHead())) {
            System.out.println("Game Over");
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
        
        if (myBarriers != null){  
            for (int i = 0; i < myBarriers.size(); i++){
                myBarriers.get(i).draw(graphics);
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
