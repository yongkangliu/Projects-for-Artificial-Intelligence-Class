package PG1AStarAlgGUI;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TileButton extends JButton {

    /**
     * 
     */
    private static final long serialVersionUID = -9181977196699131202L;

    public TileButton(String name) {
        setName(name);
        setBackground(new java.awt.Color(255, 128, 0));
        setPreferredSize(new java.awt.Dimension(50, 50));
        setMaximumSize(new Dimension(50, 50));

        setTileButtonIcon(name);

        addMouseListener(new TileMouseAdapter());
        addMouseMotionListener(null);
    }

    public void setTileButtonIcon(String name) {
        if ("1".equals(name)) {
            setIcon(new ImageIcon(this.getClass().getResource("1.png")));
        } else if ("2".equals(name)) {
            setIcon(new ImageIcon(this.getClass().getResource("2.png")));
        } else if ("3".equals(name)) {
            setIcon(new ImageIcon(this.getClass().getResource("3.png")));
        } else if ("4".equals(name)) {
            setIcon(new ImageIcon(this.getClass().getResource("4.png")));
        } else if ("5".equals(name)) {
            setIcon(new ImageIcon(this.getClass().getResource("5.png")));
        } else if ("6".equals(name)) {
            setIcon(new ImageIcon(this.getClass().getResource("6.png")));
        } else if ("7".equals(name)) {
            setIcon(new ImageIcon(this.getClass().getResource("7.png")));
        } else if ("8".equals(name)) {
            setIcon(new ImageIcon(this.getClass().getResource("8.png")));
        } else if ("0".equals(name)) {
            setIcon(new ImageIcon(this.getClass().getResource("0.png")));
        }
    }
}
