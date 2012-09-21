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

public class TileGUIPanels extends JFrame {

    private static final long serialVersionUID = 8173460509781322855L;
    private static int TILE_NUMBER = 9;

    private static TileGUIPanels tileGUIPanels = null;
    private static JTextArea textArea;
    private static int[] start;
    private static int[] goal;

    public TileButton[] initTiles = new TileButton[TILE_NUMBER];
    public TileButton[] goalTiles = new TileButton[TILE_NUMBER];

    public static void main(String[] args) {
        TileGUIPanels tileGUI = new TileGUIPanels();
        tileGUI.setVisible(true);
    }

    public static void printLog(String str) {
        TileGUIPanels.textArea.append(str);
        TileGUIPanels.textArea.append("\r\n\r\n");

    }

    public static void printLogReset(String str) {
        TileGUIPanels.textArea.setText(str);
        TileGUIPanels.textArea.append("\r\n");
        TileGUIPanels.textArea.repaint();
    }

    public TileGUIPanels() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("8 puzzle solver");

        getContentPane().add(new JLabel("      Initial State                                              Goal State"),
                BorderLayout.NORTH);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < TILE_NUMBER; i++) {
            TileButton tile = new TileButton(String.valueOf(i));
            this.initTiles[i] = tile;
            panel1.add(tile);
        }
        getContentPane().add(panel1, BorderLayout.WEST);

        JButton runButton = new JButton("RUN");
        getContentPane().add(runButton, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < TILE_NUMBER; i++) {
            TileButton tile = new TileButton(String.valueOf(i));
            this.goalTiles[i] = tile;
            panel2.add(tile);
        }
        getContentPane().add(panel2, BorderLayout.EAST);

        JTextArea jTextArea = new JTextArea(15, 5);
        jTextArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        TileGUIPanels.textArea = jTextArea;

        printLog("Usage:\r\nDrag the tiles to set the puzzle initial and goal states.\r\nThen click RUN to solve the puzzle.");
        printLog("The result will display here.");
        printLog("-- Made by Yongkang Liu for UNCC ITCS 6150 class --");

        getContentPane().add(jScrollPane, BorderLayout.SOUTH);

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
