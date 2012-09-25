/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework
 * 
 * by Yongkang Liu, 9/21/2012
 */
package PG1AStarAlgGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import PG1AStarAlg.AStar;

/**
 * Main class to run the application.
 */
public class TileGUIPanels extends JFrame {

    private static final long serialVersionUID = 8173460509781322855L;
    private static int TILE_NUMBER = 9;

    // the TileGUIPanels instance which draws all tiles in windows.
    private static TileGUIPanels tileGUIPanels = null;

    // the textArea which displays all log information.
    private static JTextArea textArea;

    // the initial state in the 9-length array
    private static int[] start;

    // the goal state in the 9-length array
    private static int[] goal;

    // store initial tiles
    public TileButton[] initTiles = new TileButton[TILE_NUMBER];

    // store goal tiles
    public TileButton[] goalTiles = new TileButton[TILE_NUMBER];

    public static void main(String[] args) {
        TileGUIPanels tileGUI = new TileGUIPanels();
        tileGUI.setVisible(true);
    }

    public static void printLog(String str) {
        if (TileGUIPanels.textArea != null) {
            TileGUIPanels.textArea.append(str);
            TileGUIPanels.textArea.append("\r\n\r\n");
        }
    }

    public static void printResetLog(String str) {
        if (TileGUIPanels.textArea != null) {
            TileGUIPanels.textArea.setText(str);
            TileGUIPanels.textArea.append("\r\n");
            TileGUIPanels.textArea.repaint();
        }
    }

    /**
     * Run A* algorithm in multi-thread mode to display the search progress in GUI.
     */
    public TileGUIPanels() {
        // setting for main frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("8 puzzle solver");

        // add label on NORTH
        getContentPane().add(new JLabel("      Initial State                                              Goal State"),
                BorderLayout.NORTH);

        // add initial state puzzle on WEST
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < TILE_NUMBER; i++) {
            TileButton tile = new TileButton(String.valueOf(i));
            this.initTiles[i] = tile;
            panel1.add(tile);
        }
        getContentPane().add(panel1, BorderLayout.WEST);

        // add RUN button on CENTER
        JButton runButton = new JButton("RUN");
        getContentPane().add(runButton, BorderLayout.CENTER);

        // add goal state puzzle on EAST
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < TILE_NUMBER; i++) {
            TileButton tile = new TileButton(String.valueOf(i));
            this.goalTiles[i] = tile;
            panel2.add(tile);
        }
        getContentPane().add(panel2, BorderLayout.EAST);

        // add text area on SOUTH
        JTextArea jTextArea = new JTextArea(15, 5);
        jTextArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        TileGUIPanels.textArea = jTextArea;
        getContentPane().add(jScrollPane, BorderLayout.SOUTH);

        // initial text area
        printLog("Usage:\r\nDrag the tiles to set the puzzle initial and goal states.\r\nThen click RUN to solve the puzzle.");
        printLog("The result will display here.");
        printLog("-- Made by Yongkang Liu for UNCC ITCS 6150 class --");

        // add action listener to all tiles
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                TileGUIPanels.start = new int[TILE_NUMBER];
                for (int i = 0; i < TILE_NUMBER; i++) {
                    TileGUIPanels.start[i] = Integer.valueOf(TileGUIPanels.tileGUIPanels.initTiles[i].getName());
                }

                TileGUIPanels.goal = new int[TILE_NUMBER];
                for (int i = 0; i < TILE_NUMBER; i++) {
                    TileGUIPanels.goal[i] = Integer.valueOf(TileGUIPanels.tileGUIPanels.goalTiles[i].getName());
                }

                // start a new thread to run A* algorithm, so the search progress can display on GUI.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AStar aStar = new AStar();

                        aStar.run(TileGUIPanels.start, TileGUIPanels.goal);
                    }
                }).start();
            }
        });

        pack();
        setResizable(false);
        TileGUIPanels.tileGUIPanels = this;
    }
}
