package PG2HillClimbingGUI;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class QueenLabel extends JLabel {

    private static final long serialVersionUID = 3368309132494005428L;

    public QueenLabel(String name) {
        setName(name);
        
        setOpaque(true); 
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(50, 50));
        setMaximumSize(new Dimension(50, 50));

        Border paneEdge = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        setBorder(paneEdge);
    }
}