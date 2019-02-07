package org.sgdshake.gol.model;

/**
 * Updates cells captured by the game model based on GOL rules applied every round / evolution
 *
 * @author sgdshake
 */
public class GameLogic {

    private final GridModel gridModel;

    /**
     * Initialises grid model, the cell config model
     *
     * @param gridModel
     */
    public GameLogic(GridModel gridModel) {
        this.gridModel = gridModel;
    }

    /**
     * Transitions to next cell configuration for next round
     */
    public void evolve() {
        updateModel();
    }

    /**
     * Checks if neighbouring cell is within the grids valid range
     *
     * @param n x or y coordinate
     * @return Validity status
     */
    private boolean checkValidity(int n) {
        return n >= 0 && n < gridModel.getXDimension() && n < gridModel.getYDimension();
    }

    /**
     * Counts the number of alive neighbouring cells for a given cell
     *
     * @param x row
     * @param y col
     * @return Number of alive neighbouring cells
     */
    private int countLivingCells(int x, int y) {
        int cellCount = 0;

        // For all neighbouring cells...
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                // Perform validity check to ensure we are not out of bounds
                if ((x != i || y != j) && checkValidity(i) && checkValidity(j)) {
                    // Increment count if neighbouring cells are alive
                    if (gridModel.getGrid()[i][j].getOccupied()) {
                        System.out.println("cell " + i + ", " + j + " found to be occupied, inc count");
                        cellCount++;
                    }
                }
            }
        }
        System.out.println("cell count for cell " + x + "," + y + ": " + cellCount);
        return cellCount;
    }

    /**
     * Updates game model based on the following GOL rules:
     * <p>
     * (1) No living cells:                  still no living cells next round
     * (2) cell < 2 neighbours:              cell dies due to underpopulation
     * (3) cell > 3 neighbours:              cell dies due to overpopulation
     * (4) cell 2 || 3 neighbours:           cell survives
     * (5) empty position has 3 neighbours:  cell is created in this
     */
    private void updateModel() {
        // For all cells in the grid...
        for (int x = 0; x < gridModel.getXDimension(); x++) {
            for (int y = 0; y < gridModel.getYDimension(); y++) {
                if (!gridModel.getGrid()[x][y].getOccupied()
                        && (countLivingCells(x, y) == 3)) {
                    // Cell can therefore multiply
                    System.out.println("setting cell " + x + ", " + y + " as occupied");
                    gridModel.getCloneGrid()[x][y].setOccupied(true);
                } else if (gridModel.getGrid()[x][y].getOccupied()
                        && (countLivingCells(x, y) == 2 || countLivingCells(x, y) == 3)) {
                    gridModel.getCloneGrid()[x][y].setOccupied(true);
                } else if (gridModel.getGrid()[x][y].getOccupied()
                        && (countLivingCells(x, y) < 2 || countLivingCells(x, y) > 3)) {
                    // Cell will dies from under / overpopulation
                    gridModel.getCloneGrid()[x][y].setOccupied(false);
                }
            }
        }
        gridModel.setGrid(gridModel.getCloneGrid());
        gridModel.clearClonedGrid();
    }
}
