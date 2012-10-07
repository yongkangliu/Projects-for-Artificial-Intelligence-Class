/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbingGUI;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * Use JLabel object to draw the chessboard.
 */
public class QueenLabel extends JLabel {

    private static final long serialVersionUID = 3368309132494005428L;

    // the tile name.
    private int name;

    // the number of queens.
    private int numOfQueens;

    /**
     * Constructor of QueenLabel
     * 
     * @param name
     *            The tile name
     * @param numOfQueens
     *            The number of queens.
     */
    public QueenLabel(int name, int numOfQueens) {
        this.name = name;
        this.numOfQueens = numOfQueens;

        // no border for chessboard.
        Border paneEdge = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        setBorder(paneEdge);

        setHorizontalAlignment(CENTER);

        // don't set queens when initialize the chessboard.
        setColor(false);
    }

    /**
     * Set the chessboard color.
     * 
     * @param isQueen
     *            True if the block is a queen. False if the block isn't a queen.
     */
    public void setColor(boolean isQueen) {
        if (isQueen) {
            // if the block is a queen, fill yellow color with Q charactor.
            setBackground(new java.awt.Color(255, 242, 0));
            setText("Q");
        } else {
            int x = name / numOfQueens;
            int y = name % numOfQueens;

            // draw the chessboard with two colors.
            if ((x + y) % 2 == 0) {
                setBackground(new java.awt.Color(255, 255, 255));
            } else {
                setBackground(new java.awt.Color(127, 127, 127));
            }

            setOpaque(true);
            setPreferredSize(new java.awt.Dimension(20, 20));
            setMaximumSize(new Dimension(50, 50));
            setText("");
        }
    }
}