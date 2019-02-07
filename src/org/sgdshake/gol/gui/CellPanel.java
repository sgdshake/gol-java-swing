package org.sgdshake.gol.gui;

import org.sgdshake.gol.model.Cell;

import javax.swing.*;
import java.awt.*;

/**
 * Jpanel used to represent each cell in the grid used in GOL
 *
 * @author sgdshake
 */
public class CellPanel extends JPanel {

    private boolean isAlive;

    /**
     * Changes cell colour to be colour which represents cell alive (defined in Cell)
     */
    public void setAlive() {
        this.setBackground(Cell.ALIVE_CELL_COLOUR);
        this.isAlive = true;
    }

    /**
     * Changes cell colour to be the colour which represents cell death (defined in Cell)
     */
    public void setDead() {
        this.setBackground(Cell.DEAD_CELL_COLOUR);
        this.isAlive = false;
    }

    /**
     * Adds black border to panel to distinguish between adjacent cells in the grid structure
     */
    public void addBorder() {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * Returns cell alive status
     *
     * @return alive == true, dead == false
     */
    public boolean isAlive() {
        return isAlive;
    }
}