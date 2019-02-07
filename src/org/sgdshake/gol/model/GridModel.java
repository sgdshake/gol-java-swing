package org.sgdshake.gol.model;

/**
 * Builds a model of the GOL grid and its associated cells used in the
 * calculations of new cell configurations and subsequent
 * updates after each evolution.
 *
 * @author sgdshake
 */
public class GridModel {

    private Cell[][] grid;
    private Cell[][] clone;
    private final int xDimension;
    private final int yDimension;

    /**
     * Initialises grid x and y dimensions and makes call to create grid,
     * a multidimensional array of Cells
     *
     * @param xDimension
     * @param yDimension
     */
    public GridModel(int xDimension, int yDimension) {
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        createGrid();
    }

    /**
     * Getter method to retrieve the grids x dimension
     *
     * @return xDimension
     */
    public int getXDimension() {
        return xDimension;
    }


    /**
     * Getter method to retrieve the grids y dimension
     *
     * @return yDimension
     */
    public int getYDimension() {
        return yDimension;
    }

    /**
     * Getter method to retrieve cloned grid object
     *
     * @return Cloned grid, the cloned multidimensional array of cells
     */
    public Cell[][] getCloneGrid() {
        return clone;
    }

    /**
     * Getter method to retrieve grid object
     *
     * @return Grid, the multidimensional array of cells
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Setter method to initialise grid object
     *
     * @param grid
     */
    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    /**
     * Reinitilies cloned grid object ready for next round
     */
    public void clearClonedGrid() {
        clone = new Cell[xDimension][yDimension];

        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                clone[x][y] = new Cell();
            }
        }
    }

    /**
     * Creates multi-dimensional array of cells to represent the grid
     */
    public void createGrid() {
        grid = new Cell[xDimension][yDimension];
        // Clone created to prevent interference during grid update
        clone = new Cell[xDimension][yDimension];

        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                grid[x][y] = new Cell();
                clone[x][y] = new Cell();
            }
        }
    }
}
