package org.sgdshake.gol.controller;

import org.sgdshake.gol.listeners.RoundChangeListener;
import org.sgdshake.gol.model.GameLogic;
import org.sgdshake.gol.model.GridModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements main evolution thread used to simulate cell evolution
 *
 * @author sgdshake
 */
public class RunGameController implements ActionListener {

    private GridModel gModel;
    private GameLogic gameLogic;
    private volatile CellGridController cellGridController;
    private boolean running;
    private boolean paused;
    private List<RoundChangeListener> listeners = new ArrayList<RoundChangeListener>();

    /**
     * Initialises instances
     *
     * @param gModel
     * @param cellGridController
     */
    public RunGameController(GridModel gModel, CellGridController cellGridController) {
        this.gModel = gModel;
        this.cellGridController = cellGridController;
        gameLogic = new GameLogic(gModel);
    }

    /**
     * Implements apporpriate action in accordance with which JButton was pressed by user
     *
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton b = (JButton) actionEvent.getSource();
        switch (b.getText()) {
            case "Stop":
                stopGame();
                break;
            case "Start":
                startLifeCycle();
                break;
            case "Clear":
                resetCells();
                break;
            case "Pause":
                pauseGame();
                break;
            case "Resume":
                restartGame();
                break;
            default:
        }
    }

    /**
     * Add new listener to respond to round changes
     *
     * @param listener
     */
    public void addRoundChangeListener(RoundChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Initialises evolution thread and its appropriate flags used to pause the thread
     */
    private void startLifeCycle() {
        running = true;
        paused = false;
        new Thread(new EvolutionThread()).start();
    }

    /**
     * Resets the game model such that all cell objects are now considered dead
     */
    private void resetCells() {
        gModel.createGrid();
    }

    /**
     * Updates flags used to pause evolution thread
     */
    private void pauseGame() {
        paused = true;
    }

    /**
     * Updates flags used to pause evolution thread
     */
    private void restartGame() {
        paused = false;
    }

    /**
     * Updates flags used to stop evolution thread running
     */
    private void stopGame() {
        running = false;
        paused = true; // To break from 2nd while loop
    }

    /**
     * Triggers all listeners which implement RoundChangeListener
     *
     * @param roundValue new round value
     */
    private void fireRoundChange(int roundValue) {
        for (RoundChangeListener listener : listeners) {
            listener.roundChanged(roundValue);
        }
    }

    /**
     * Main thread used to simulate cell evolution relative to JButton last pressed
     */
    private class EvolutionThread implements Runnable {

        @Override
        public void run() {
            int roundNum = 0;
            while (running) {
                try {
                    int SPEED = 250;
                    Thread.sleep(SPEED);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                while (!paused) {
                    roundNum++;
                    fireRoundChange(roundNum);
                    gameLogic.evolve();
                    cellGridController.fillCellPanelGrid();

                    try {
                        int SPEED = 250;
                        Thread.sleep(SPEED);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            System.out.println("thread stopping");
        }
    }
}
