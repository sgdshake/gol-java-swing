package org.sgdshake.gol.model;

import java.awt.*;

/**
 * Encapsulates cells occupation status and it's possible associated colours
 * used in the GUI
 *
 * @author sgdshake
 */
public class Cell {

    public static final Color ALIVE_CELL_COLOUR = Color.green;
    public static final Color DEAD_CELL_COLOUR = Color.white;
    public static final Color CELL_POINTER_COLOUR = Color.lightGray;

    private boolean occupied;

    /**
     * Setter method to initialise cell occupation status
     *
     * @param status dead or alive
     */
    public void setOccupied(boolean status) {
        occupied = status;
    }

    /**
     * Getter method to retrieve cell occupation status
     *
     * @return boolean, dead == false, alive == true
     */
    public boolean getOccupied() {
        return occupied;
    }
}
