package org.sgdshake.gol.controller;

import org.sgdshake.gol.gui.CellPanel;
import org.sgdshake.gol.gui.GridGui;
import org.sgdshake.gol.midi.MidiPlayer;
import org.sgdshake.gol.model.Cell;
import org.sgdshake.gol.model.GridModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * JPanel used to encapsulate individual cell panel JPanels and control their modification
 *
 * @author sgdshake
 */
public class CellGridController extends JPanel {

    private final int xCellCount;
    private final int yCellCount;
    private final GridModel gridModel;
    private final MidiPlayer midiPlayer;

    /**
     * Init class to use midi so that sounds can be played for every cell evolution
     *
     * @param gridModel
     * @param midiPlayer
     */
    public CellGridController(GridModel gridModel, MidiPlayer midiPlayer) {
        this.gridModel = gridModel;
        this.midiPlayer = midiPlayer;
        this.xCellCount = GridGui.X_DIMENSION;
        this.yCellCount = GridGui.Y_DIMENSION;
        initialize();
        fillCellPanelGrid();
    }

    /**
     * Init class for non-midi scenario (i.e. no sound to be played)
     *
     * @param gridModel
     */
    public CellGridController(GridModel gridModel) {
        this(gridModel, null);
    }

    /**
     * Updates cell views based on the updated model
     */
    public void fillCellPanelGrid() {
        CellPanel cellPanel;
        for (int i = 0; i < xCellCount; i++) {
            for (int j = 0; j < yCellCount; j++) {
                cellPanel = getCellPanelAtPosition(i, j);
                if (gridModel.getGrid()[i][j].getOccupied()) {
                    System.out.println("found occupied grid");
                    cellPanel.setAlive();
                    if (midiPlayer != null) {
                        midiPlayer.init(midiPlayer.mapToMidiRange(i, j, 0, (xCellCount * yCellCount)));
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    cellPanel.setDead();
                }
            }
        }
    }

    /**
     * Sets all cell panel objects associated colours to the colour associated with dead cells
     */
    public void resetGrid() {
        for (int i = 0; i < xCellCount * yCellCount; i++) {
            int row = i / xCellCount;
            int col = i % yCellCount;
            getCellPanelAtPosition(row, col).setDead();
        }
    }

    /**
     * Initialises JPanel layout and makes call to add cell panels to it
     */
    private void initialize() {
        this.setBackground(Color.gray);
        this.setLayout(new GridLayout(xCellCount, yCellCount));
        addCellPanels();
    }

    /**
     * Creates CellPanel objects and adds them to its own JPanel
     */
    private void addCellPanels() {
        CellPanel cellPanel;
        for (int i = 0; i < xCellCount * yCellCount; i++) {
            int row = i / xCellCount;
            int col = i % yCellCount;
            cellPanel = createCellPanel(row, col);
            cellPanel.addBorder();
            this.add(cellPanel);
        }
    }

    /**
     * Creates a CellPanel object at a 2D position and adds the mouseListener event handler
     *
     * @param x rows
     * @param y cols
     * @return CellPanel
     */
    private CellPanel createCellPanel(final int x, final int y) {
        final CellPanel cellPanel = new CellPanel();
        cellPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                modifyCellPanelAtPosition(x, y);
                updateCellModel(x, y);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                paintMouseExitedCell(x, y);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                paintMouseEnteredCell(x, y);
            }

        });
        return cellPanel;
    }

    /**
     * Modifies the colour and occupation status of a cellPanel for a requested position
     *
     * @param x rows
     * @param y cols
     */
    private void modifyCellPanelAtPosition(int x, int y) {
        CellPanel cellPanel = getCellPanelAtPosition(x, y);
        if (cellPanel.isAlive()) {
            cellPanel.setDead();
        } else {
            cellPanel.setAlive();
        }
    }

    /**
     * Changes the colour of requested cellPanel when the users cursor hovers over it to indicate cell being considered
     *
     * @param x rows
     * @param y cols
     */
    private void paintMouseEnteredCell(int x, int y) {
        getCellPanelAtPosition(x, y).setBackground(Cell.CELL_POINTER_COLOUR);
    }

    /**
     * Changes the color of requested cellPanel back to its previous colour before the users cursor had entered it
     *
     * @param x rows
     * @param y cols
     */
    private void paintMouseExitedCell(int x, int y) {
        CellPanel cellPanel = getCellPanelAtPosition(x, y);
        if (cellPanel.isAlive()) {
            cellPanel.setBackground(Cell.ALIVE_CELL_COLOUR);
        } else {
            cellPanel.setBackground(Cell.DEAD_CELL_COLOUR);
        }
    }

    /**
     * Updates a cells occupation status in the grid model
     *
     * @param x
     * @param y
     */
    private void updateCellModel(int x, int y) {
        boolean occupied = gridModel.getGrid()[x][y].getOccupied();
        gridModel.getGrid()[x][y].setOccupied(!occupied);
    }

    /**
     * Gets cell panel object at requested position
     *
     * @param x col
     * @param y row
     * @return CellPanel
     */
    private CellPanel getCellPanelAtPosition(int x, int y) {
        int index = x * xCellCount + y;
        return (CellPanel) getComponent(index);
    }
}