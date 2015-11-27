/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tron;

import environment.ApplicationStarter;
import grid.Grid;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Nick
 */
public class Tron {

    public Tron(Direction direction, Grid grid) {
        this.direction = direction;
        this.grid = grid;

        body = new ArrayList<>();
        body.add(new Point(5, 5));
        body.add(new Point(5, 4));
        body.add(new Point(5, 3));

    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationStarter.run("Tron", new Computer());
    }

    public void draw(Graphics graphics) {
        graphics.setColor(getBodyColor());

        for (int i = 0; i < getBody().size(); i++) {
            //System.out.println("body location = " + body.get(i).toString());
            graphics.fillRect(getGrid().getCellSystemCoordinate(getBody().get(i)).x,
                    getGrid().getCellSystemCoordinate(getBody().get(i)).y,
                    getGrid().getCellWidth(),
                    getGrid().getCellHeight());

        }
    }

    public void move() {
        Point newHead = new Point(getHead());

        if (getDirection() == Direction.Left) {
            newHead.x--;
        } else if (getDirection() == Direction.Right) {
            newHead.x++;
        } else if (getDirection() == Direction.Up) {
            newHead.y--;
        } else if (getDirection() == Direction.Down) {
            newHead.y++;
        }
        //add the new head
        getBody().add(HEAD_POSITION, newHead);
    }

    
    public Point getHead() {
        return getBody().get(HEAD_POSITION);

    }

//<editor-fold defaultstate="collapsed" desc="Properties">
    private static final int HEAD_POSITION = 0;
    
    
    private Direction direction = Direction.Left;
    private ArrayList<Point> body;
    private Grid grid;
    private Color bodyColor = Color.MAGENTA;
    
    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }
    
    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }
    
    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }
    
    /**
     * @return the grid
     */
    public Grid getGrid() {
        return grid;
    }
    
    /**
     * @param grid the grid to set
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }
    
    /**
     * @return the bodyColor
     */
    public Color getBodyColor() {
        return bodyColor;
    }
    
    /**
     * @param bodyColor the bodyColor to set
     */
    public void setBodyColor(Color bodyColor) {
        this.bodyColor = bodyColor;
    }
//</editor-fold>
}
