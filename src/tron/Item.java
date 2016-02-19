/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tron;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author Nick
 */
public class Item {
    
    public void draw(Graphics graphics){
        graphics.drawImage(image, 
                            cellData.getSystemCoordX(x, y),
                            cellData.getSystemCoordY(x, y),
                            cellData.getCellWidth(),
                            cellData.getCellhight(), null);
    }
    
    public Item(int x, int y, String type, Image image,
                  CellDataProviderIntf cellData){
        this.x = x;
        this.y = y;
        this.type = type;
        this.image = image;
        this.cellData = cellData;
        // Item werden zufaellig gespawned und die Items Von JB1 werden gut fuer Jb1 ( Item verschnellern JB1) sein Werden allerdings JB2 verlangsammen. 
        // Die kann ein Vorteil fuer Jb1 und JB2 sein. 
        // Also JB 1 Pub Gut / Schlecht JB2
        // Also JB 2 PUY Gut / Schlecht JB1
    }
    
    public static final String POWER_UP_MAGIC = "MAGIC";
    public static final String POWER_UP_BLUE = "PUB";
    public static final String POWER_UP_ORANGE = "PUY";
    public static final String POWER_UP_INVULNERABLE = "PINV";
    
    //<editor-fold defaultstate="collapsed" desc="Properties Items">
    private int x, y;
    private String type;
    private Image image;
    private CellDataProviderIntf cellData;

    /**
     * @return the x
     */
    public Point getLocation() {
        return new Point(x, y);
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }
    
    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * @return the y
     */
    public int getY() {
        return y;
    }
    
    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }
    
    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }
//</editor-fold>
    
}
