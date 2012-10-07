/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbingGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import PG2HillClimbing.HillClimbing;
import PG2HillClimbing.MinConflicts;

/**
 * Main class to run the application.
 */
public class QueensGUI extends JFrame {

    private static final long serialVersionUID = -7636271449210116618L;

    // maximum queens in chessboard
    private static final int MAX_DISPLAY_QUEENS = 100;

    // the text field for the number of queens.
    private static JTextField numOfQueens;

    // the hill climbing algorithem option.
    private static JRadioButton hillClimbing;

    // the text area for information.
    private static JTextArea textArea;

    // the chessboard
    private static JPanel chessPanel;

    // the problem state data.
    private static int[] states = new int[] {};

    /**
     * The main fucntion.
     * 
     * @param args
     */
    public static void main(String[] args) {
        QueensGUI queensGUI = new QueensGUI();
        queensGUI.setVisible(true);
    }

    /**
     * Run application in multi-thread mode to display the progress in GUI.
     */
    public QueensGUI() {
        // Initialize the frame.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("8 Queens Solver");

        // Initialize the options of algorithms.
        JPanel panel0 = new JPanel();
        panel0.setLayout(new GridLayout(2, 3));
        panel0.add(new JLabel("Select Algorithm:"));

        ButtonGroup group = new ButtonGroup();
        JRadioButton button1 = new JRadioButton("Hill Climbing (slow)");
        hillClimbing = button1;
        group.add(button1);
        panel0.add(button1);

        JRadioButton button2 = new JRadioButton("Min Conflicts (fast)");
        button2.setSelected(true);// default algorithm
        group.add(button2);
        panel0.add(button2);
        panel0.add(new JLabel("Input num of Queens:"));

        // initialize the number of queens.
        JTextField text = new JTextField("25");
        panel0.add(text);
        QueensGUI.numOfQueens = text;

        // add RUN button
        JButton runButton = new JButton("RUN");
        panel0.add(runButton);

        getContentPane().add(panel0, BorderLayout.NORTH);

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // repaint chessboard.
                drawChessboard();

                if (QueensGUI.hillClimbing.isSelected()) {
                    // start a new thread to run Hill Climbing algorithm.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar startTime = Calendar.getInstance();

                            HillClimbing hc = new HillClimbing();
                            int[] state = hc.run(Integer.valueOf(QueensGUI.numOfQueens.getText()));

                            Calendar endTime = Calendar.getInstance();
                            QueensGUI.appendLog("Time-consuming (seconds): "
                                    + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);

                            QueensGUI.moveQueens(state);
                        }
                    }).start();

                } else {
                    // start a new thread to run Min-Conflicts algorithm.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar startTime = Calendar.getInstance();

                            MinConflicts mc = new MinConflicts();
                            int[] state = mc.run(Integer.valueOf(QueensGUI.numOfQueens.getText()));

                            Calendar endTime = Calendar.getInstance();
                            QueensGUI.appendLog("Time-consuming (seconds): "
                                    + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);

                            QueensGUI.moveQueens(state);
                        }
                    }).start();
                }

            }
        });

        // Initialize the chessboard.
        JPanel panel1 = new JPanel();
        panel1.removeAll();
        QueensGUI.chessPanel = panel1;
        drawChessboard();
        getContentPane().add(QueensGUI.chessPanel, BorderLayout.CENTER);
        QueensGUI.moveQueens(new int[] { 15, 13, 11, 9, 7, 6, 5, 6, 7, 9, 11, 13, 14, 13, 11, 10, 11, 12, 11, 10, 11,
                12, 11, 11, 11 });

        // add text area on SOUTH
        JTextArea jTextArea = new JTextArea(10, 5);
        jTextArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        QueensGUI.textArea = jTextArea;
        getContentPane().add(jScrollPane, BorderLayout.SOUTH);

        // initialize the usage information.
        QueensGUI.textArea.append("-- Usage:\r\n");
        QueensGUI.textArea.append("-- 1. Select algorithem: Hill Climbing algorithem or Min-Conflicts algorithem.\r\n");
        QueensGUI.textArea
                .append("-- 2. Input number of Queens. If number is larger than 100, Chessboard doesn't display.\r\n");
        QueensGUI.textArea.append("-- 3. Click the RUN botton to start.\r\n");
        QueensGUI.textArea.append("-- 4. The result displays here. The yellow tile with Q character is the Queen.\r\n");
        QueensGUI.textArea.append("-- Made by Yongkang Liu for UNCC ITCS 6150 class --");

        pack();
        setResizable(false);
    }

    /**
     * Move the queens and set the queen block's color.
     * 
     * @param newStates
     *            The problem state data.
     */
    public static void moveQueens(int[] newStates) {
        if (QueensGUI.chessPanel != null && QueensGUI.chessPanel.getComponentCount() > 0) {
            if (QueensGUI.states.length > 0) {
                // if there is a previous state data, just repaint the moved queen.

                for (int x = 0; x < newStates.length; x++) {
                    if (QueensGUI.states[x] != newStates[x]) {
                        // clean the previous position of the queen.
                        QueenLabel label = (QueenLabel) QueensGUI.chessPanel.getComponent(x + QueensGUI.states[x]
                                * QueensGUI.states.length);
                        label.setColor(false);

                        // paint the new position of the queen.
                        QueenLabel newLabel = (QueenLabel) QueensGUI.chessPanel.getComponent(x + newStates[x]
                                * newStates.length);
                        newLabel.setColor(true);
                    }
                }
            } else {
                // if there isn't a previous state data, repaint all queens.

                for (int i = 0; i < newStates.length; i++) {
                    QueenLabel newLabel = (QueenLabel) QueensGUI.chessPanel.getComponent(i + newStates[i]
                            * newStates.length);
                    newLabel.setColor(true);
                }
            }

            // record the new state data.
            QueensGUI.states = newStates;

            QueensGUI.chessPanel.revalidate();
            QueensGUI.chessPanel.repaint();
        }
    }

    /**
     * Draw the chessboard
     */
    private void drawChessboard() {
        // get the number of queens.
        int num = Integer.valueOf(QueensGUI.numOfQueens.getText());

        if (num * num != QueensGUI.chessPanel.getComponentCount()) {
            QueensGUI.chessPanel.setLayout(new GridLayout(num, num));

            // remove previous chessboard
            QueensGUI.states = new int[] {};
            QueensGUI.chessPanel.removeAll();

            if (num <= QueensGUI.MAX_DISPLAY_QUEENS) {
                // only draw limited queens due to the screen size.
                for (int i = 0; i < (num * num); i++) {
                    QueenLabel queen = new QueenLabel(i, num);
                    QueensGUI.chessPanel.add(queen);
                }
            }
            QueensGUI.chessPanel.revalidate();
            QueensGUI.chessPanel.repaint();
        }
    }

    /**
     * Append information in the EditText.
     * 
     * @param str
     *            the information
     */
    public static void appendLog(String str) {
        if (QueensGUI.textArea != null) {
            QueensGUI.textArea.append(str);
            QueensGUI.textArea.append("\r\n\r\n");
        }
    }

    /**
     * Clean old information and print new information in the EditText.
     * 
     * @param str
     *            the new information
     */
    public static void resetLog(String str) {
        if (QueensGUI.textArea != null) {
            QueensGUI.textArea.setText(str);
            QueensGUI.textArea.append("\r\n");
            QueensGUI.textArea.repaint();
        }
    }
}
