package PG1AStarAlgGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import PG1AStarAlg.AStar;

public final class TileGUIPanels extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 8173460509781322855L;
    public static int TILE_NUMBER = 9;
    public TileButton[] initTiles = new TileButton[TILE_NUMBER];
    public TileButton[] goalTiles = new TileButton[TILE_NUMBER];

    public static void main(String[] args) {
        TileGUIPanels tileGUI = new TileGUIPanels();
        tileGUI.setVisible(true);
    }

    public TileGUIPanels() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < TILE_NUMBER; i++) {
            TileButton tile = new TileButton(String.valueOf(i));
            this.initTiles[i] = tile;
            panel1.add(tile);
        }
        getContentPane().add(panel1, BorderLayout.NORTH);

        JButton runButton = new JButton("run");
        getContentPane().add(runButton);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < TILE_NUMBER; i++) {
            TileButton tile = new TileButton(String.valueOf(i));
            this.goalTiles[i] = tile;
            panel2.add(tile);
        }
        getContentPane().add(panel2, BorderLayout.SOUTH);

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JButton runButton = (JButton) event.getSource();
                TileGUIPanels tileGUIPanels = (TileGUIPanels) runButton.getParent().getParent().getParent().getParent();

                int[] start = new int[TILE_NUMBER];
                for (int i = 0; i < TILE_NUMBER; i++) {
                    start[i] = Integer.valueOf(tileGUIPanels.initTiles[i].getName());
                }

                int[] goal = new int[TILE_NUMBER];
                for (int i = 0; i < TILE_NUMBER; i++) {
                    goal[i] = Integer.valueOf(tileGUIPanels.goalTiles[i].getName());
                }
                AStar aStar = new AStar();

                aStar.run(start, goal);
            }
        });

        pack();
        setResizable(false);
    }

}
