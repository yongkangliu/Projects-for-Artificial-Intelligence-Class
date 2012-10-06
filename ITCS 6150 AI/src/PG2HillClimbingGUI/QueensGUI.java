package PG2HillClimbingGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class QueensGUI extends JFrame {

    private static final long serialVersionUID = -7636271449210116618L;
    private List<QueenLabel> queensList = new ArrayList<QueenLabel>();

    public static void main(String[] args) {
        QueensGUI queensGUI = new QueensGUI();
        queensGUI.setVisible(true);
    }

    public QueensGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("8 Queens Solver");

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(5, 5));
        for (int i = 0; i < 25; i++) {
            QueenLabel tile = new QueenLabel(String.valueOf(i));
            panel1.add(tile);
            this.queensList.add(tile);
        }
        getContentPane().add(panel1, BorderLayout.CENTER);

        pack();
        setResizable(false);
    }
}
