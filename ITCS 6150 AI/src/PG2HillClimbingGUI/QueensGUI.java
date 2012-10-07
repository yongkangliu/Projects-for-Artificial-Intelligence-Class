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
    private static List<QueenLabel> queensList = new ArrayList<QueenLabel>();
    private static JTextField numOfQueens;
    private static JRadioButton hillClimbing;
    private static JTextArea textArea;

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
        JRadioButton button1 = new JRadioButton("Hill Climbing");
        button1.setSelected(true);// default algorithm
        hillClimbing = button1;
        group.add(button1);
        panel0.add(button1);

        JRadioButton button2 = new JRadioButton("Min Conflicts");
        group.add(button2);
        panel0.add(button2);
        // Initialize the number of queens.
        panel0.add(new JLabel("Input num of Queens:"));

        JTextField text = new JTextField("20");
        panel0.add(text);
        QueensGUI.numOfQueens = text;
        JButton runButton = new JButton("RUN");
        panel0.add(runButton);

        getContentPane().add(panel0, BorderLayout.NORTH);

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                if (QueensGUI.hillClimbing.isSelected()) {
                    // start a new thread to run A* algorithm, so the search progress can display on GUI.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar startTime = Calendar.getInstance();

                            HillClimbing hc = new HillClimbing();
                            hc.run(Integer.valueOf(QueensGUI.numOfQueens.getText()));

                            Calendar endTime = Calendar.getInstance();
                            QueensGUI.appendLog("Time-consuming (seconds): "
                                    + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);
                        }
                    }).start();

                } else {
                    // start a new thread to run A* algorithm, so the search progress can display on GUI.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar startTime = Calendar.getInstance();

                            MinConflicts mc = new MinConflicts();
                            mc.run(Integer.valueOf(QueensGUI.numOfQueens.getText()));

                            Calendar endTime = Calendar.getInstance();
                            QueensGUI.appendLog("Time-consuming (seconds): "
                                    + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);
                        }
                    }).start();
                }

            }
        });

        // Draw the queens
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(5, 5));
        for (int i = 0; i < 25; i++) {
            QueenLabel tile = new QueenLabel(String.valueOf(i));
            panel1.add(tile);
            QueensGUI.queensList.add(tile);
        }
        getContentPane().add(panel1, BorderLayout.CENTER);

        // add text area on SOUTH
        JTextArea jTextArea = new JTextArea(10, 5);
        jTextArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        QueensGUI.textArea = jTextArea;
        getContentPane().add(jScrollPane, BorderLayout.SOUTH);
        pack();
        setResizable(false);
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
