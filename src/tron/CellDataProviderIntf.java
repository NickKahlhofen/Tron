/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tron;

/**
 *
 * @author Nick
 */
public interface CellDataProviderIntf {
    
    public int getCellWidth();
    public int getCellhight();
    
    public int getSystemCoordX(int x, int y);
    public int getSystemCoordY(int x, int y);
    
}
