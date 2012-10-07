package PG2HillClimbingGUI;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class QueenLabel extends JLabel {

    private static final long serialVersionUID = 3368309132494005428L;
    private int name;
    private int numOfQueens;

    public QueenLabel(int name, int numOfQueens) {
        this.name = name;
        this.numOfQueens = numOfQueens;

        setColor(false);

        Border paneEdge = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        setBorder(paneEdge);

        setHorizontalAlignment(CENTER);
    }

    public void setColor(boolean isQueen) {
        if (isQueen) {
            setBackground(new java.awt.Color(255, 242, 0));
            setText("Q");
        } else {
            int x = name / numOfQueens;
            int y = name % numOfQueens;

            setOpaque(true);
            if ((x + y) % 2 == 0) {
                setBackground(new java.awt.Color(255, 255, 255));
            } else {
                setBackground(new java.awt.Color(127, 127, 127));
            }
            setPreferredSize(new java.awt.Dimension(20, 20));
            setMaximumSize(new Dimension(50, 50));
            setText("");
        }
    }
}