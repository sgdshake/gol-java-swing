package org.sgdshake.gol.gui;

import org.sgdshake.gol.controller.CellGridController;
import org.sgdshake.gol.controller.RunGameController;
import org.sgdshake.gol.listeners.RoundChangeListener;
import org.sgdshake.gol.model.GridModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Creates the grid GUI used to display cell configurations and JButtons used to trigger
 * cell evolution
 *
 * @author sgdshake
 */
public class GridGui implements ActionListener, RoundChangeListener {

    public static final int X_DIMENSION = 40;
    public static final int Y_DIMENSION = 40;

    private CellGridController cellGridControl;
    private JButton startButton = new JButton("Start");
    private JButton clearButton = new JButton("Clear");
    private JButton stopButton = new JButton("Stop");
    private JButton pauseButton = new JButton("Pause");
    private JLabel roundDisplay = new JLabel("Round: ");
    private RunGameController rgc;

    /**
     * Initialises GUI which then awaits button press to trigger cell evolution
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        GridGui g = new GridGui();
        g.initGUI();
    }

    /**
     * Initialises models and controllers and sets up appropriate listeners for buttons used to trigger
     * cell evolution.
     */
    public GridGui() {
        GridModel gModel = new GridModel(GridGui.X_DIMENSION, GridGui.Y_DIMENSION);
        // If wish to use MIDI then use alternative constructor : new CellGridController(gModel, new MidiPlayer());
        cellGridControl = new CellGridController(gModel);
        rgc = new RunGameController(gModel, cellGridControl);
        // Add this class as a round listener so we can update the round value as it changes
        rgc.addRoundChangeListener(this);
    }

    /**
     * Fires when JButton pressed, appropriate action executed
     *
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton b = (JButton) actionEvent.getSource();
        String butText = b.getText();
        switch (butText) {
            case "Stop":
                showSetupButtons(true);
                break;
            case "Start":
                showSetupButtons(false);
                break;
            case "Pause":
                pauseButton.setText("Resume");
                break;
            case "Resume":
                pauseButton.setText("Pause");
                break;
            case "Clear":
                cellGridControl.resetGrid();
                break;
            default:
        }
    }

    /**
     * Initialises frames to be used to display grid configuration for GOL
     * Also initialises layout of start clear, pause and stop buttons
     */
    private void initGUI() {
        int MIN_SIZE = 400;

        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(MIN_SIZE, MIN_SIZE));
        frame.setSize(new Dimension(MIN_SIZE + 100, MIN_SIZE + 100));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(cellGridControl, BorderLayout.CENTER);

        // Dashboard: south
        JPanel flowLayoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        flowLayoutPanel.add(roundDisplay);

        flowLayoutPanel.add(new JSeparator(SwingConstants.VERTICAL));

        clearButton.setMnemonic(KeyEvent.VK_C);
        flowLayoutPanel.add(clearButton);

        flowLayoutPanel.add(new JSeparator(SwingConstants.VERTICAL));

        startButton.setMnemonic(KeyEvent.VK_S);
        flowLayoutPanel.add(startButton);

        pauseButton.setMnemonic(KeyEvent.VK_P);
        flowLayoutPanel.add(pauseButton);

        flowLayoutPanel.add(new JSeparator(SwingConstants.VERTICAL));

        stopButton.setMnemonic(KeyEvent.VK_T);
        flowLayoutPanel.add(stopButton);

        showSetupButtons(true);

        // Add relevant action listeners to our buttons
        addActionListeners();

        mainPanel.add(flowLayoutPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Adds listeners to detect button presses to alter game execution
     */
    private void addActionListeners() {
        clearButton.addActionListener(this);
        startButton.addActionListener(this);
        pauseButton.addActionListener(this);
        stopButton.addActionListener(this);

        clearButton.addActionListener(rgc);
        startButton.addActionListener(rgc);
        pauseButton.addActionListener(rgc);
        stopButton.addActionListener(rgc);
    }

    /**
     * Displays relevant setup buttons applicable to the current game scenario
     *
     * @param showSetupButtons
     */
    private void showSetupButtons(boolean showSetupButtons) {
        startButton.setVisible(showSetupButtons);
        clearButton.setVisible(showSetupButtons);
        pauseButton.setVisible(!showSetupButtons);
        stopButton.setVisible(!showSetupButtons);
    }

    /**
     * Sets round new round value to be displayed
     *
     * @param roundValue
     */
    @Override
    public void roundChanged(int roundValue) {
        roundDisplay.setText("Round: " + roundValue);
    }
}