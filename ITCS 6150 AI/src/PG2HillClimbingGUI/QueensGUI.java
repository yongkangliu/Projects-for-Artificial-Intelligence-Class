package PG2HillClimbingGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

public class QueensGUI extends JFrame {

    private static final long serialVersionUID = -7636271449210116618L;
    private static final int MAX_DISPLAY_QUEENS = 100;
    private static List<QueenLabel> queensList = new ArrayList<QueenLabel>();
    private static JTextField numOfQueens;
    private static JRadioButton hillClimbing;
    private static JTextArea textArea;
    private static JPanel chessPanel;
    private static int[] states = new int[] {};

    public static void main(String[] args) {
        QueensGUI queensGUI = new QueensGUI();
        queensGUI.setVisible(true);
    }

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
        button1.setSelected(true);// default algorithm
        hillClimbing = button1;
        group.add(button1);
        panel0.add(button1);

        JRadioButton button2 = new JRadioButton("Min Conflicts (fast)");
        group.add(button2);
        panel0.add(button2);
        // Initialize the number of queens.
        panel0.add(new JLabel("Input num of Queens:"));

        JTextField text = new JTextField("25");
        panel0.add(text);
        QueensGUI.numOfQueens = text;
        JButton runButton = new JButton("RUN");
        panel0.add(runButton);

        getContentPane().add(panel0, BorderLayout.NORTH);

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                drawChessboard();

                if (QueensGUI.hillClimbing.isSelected()) {
                    // start a new thread to run A* algorithm, so the search progress can display on GUI.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar startTime = Calendar.getInstance();

                            HillClimbing hc = new HillClimbing();
                            int[] state = hc.run(Integer.valueOf(QueensGUI.numOfQueens.getText()));

                            Calendar endTime = Calendar.getInstance();
                            QueensGUI.appendLog("Time-consuming (seconds): "
                                    + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);

                            QueensGUI.drawQueens(state);
                        }
                    }).start();

                } else {
                    // start a new thread to run A* algorithm, so the search progress can display on GUI.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar startTime = Calendar.getInstance();

                            MinConflicts mc = new MinConflicts();
                            int[] state = mc.run(Integer.valueOf(QueensGUI.numOfQueens.getText()));

                            Calendar endTime = Calendar.getInstance();
                            QueensGUI.appendLog("Time-consuming (seconds): "
                                    + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);

                            QueensGUI.drawQueens(state);
                        }
                    }).start();
                }

            }
        });

        JPanel panel1 = new JPanel();
        panel1.removeAll();
        QueensGUI.chessPanel = panel1;
        drawChessboard();
        getContentPane().add(QueensGUI.chessPanel, BorderLayout.CENTER);
        drawQueens(new int[] { 15, 13, 11, 9, 7, 6, 5, 6, 7, 9, 11, 13, 14, 13, 11, 10, 11, 12, 11, 10, 11, 12, 11, 11,
                11 });

        // add text area on SOUTH
        JTextArea jTextArea = new JTextArea(10, 5);
        jTextArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        QueensGUI.textArea = jTextArea;
        getContentPane().add(jScrollPane, BorderLayout.SOUTH);

        QueensGUI.textArea.append("--Usage:\r\n");
        QueensGUI.textArea.append("--1. Select algorithem: Hill Climbing algorithem or Min-Conflicts algorithem.\r\n");
        QueensGUI.textArea.append("--2. Input the number of Queens.\r\n");
        QueensGUI.textArea.append("--3. Click the RUN botton to start.\r\n");
        QueensGUI.textArea.append("--4. The result displays here. The yellow tile with Q character is the Queen.\r\n");

        pack();
        setResizable(false);
    }

    public static void drawQueens(int[] newStates) {
        if (QueensGUI.chessPanel != null && QueensGUI.chessPanel.getComponentCount() > 0) {
            if (QueensGUI.states.length > 0) {
                for (int i = 0; i < newStates.length; i++) {
                    if (QueensGUI.states[i] != newStates[i]) {
                        QueenLabel label = (QueenLabel) QueensGUI.chessPanel.getComponent(i + QueensGUI.states[i]
                                * QueensGUI.states.length);
                        label.setColor(false);
                        QueenLabel newLabel = (QueenLabel) QueensGUI.chessPanel.getComponent(i + newStates[i]
                                * newStates.length);
                        newLabel.setColor(true);
                    }
                }
            } else {
                for (int i = 0; i < newStates.length; i++) {
                    QueenLabel newLabel = (QueenLabel) QueensGUI.chessPanel.getComponent(i + newStates[i]
                            * newStates.length);
                    newLabel.setColor(true);
                }
            }
            QueensGUI.states = newStates;

            QueensGUI.chessPanel.revalidate();
            QueensGUI.chessPanel.repaint();
        }
    }

    private void drawChessboard() {
        // Draw the queens
        int num = Integer.valueOf(QueensGUI.numOfQueens.getText());

        if (num * num != QueensGUI.chessPanel.getComponentCount()) {
            QueensGUI.states = new int[] {};

            QueensGUI.chessPanel.setLayout(new GridLayout(num, num));
            QueensGUI.chessPanel.removeAll();
            if (num <= QueensGUI.MAX_DISPLAY_QUEENS) {
                for (int i = 0; i < (num * num); i++) {
                    QueenLabel queen = new QueenLabel(i, num);
                    QueensGUI.chessPanel.add(queen);
                    QueensGUI.queensList.add(queen);
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
