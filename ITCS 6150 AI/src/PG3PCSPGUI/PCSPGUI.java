package PG3PCSPGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import PG3PCSP.PCSP;
import PG3PCSP.ScheduleState;

/**
 * TableDemo is just like SimpleTableDemo, except that it uses a custom TableModel.
 */
public class PCSPGUI extends JPanel {

    private static final long serialVersionUID = 2554225509034244221L;
    private static final Color BK_COLOR = new Color(198, 198, 198);
    private ScheduleTableModel tableModel;
    private JTable table;
    private JTextField numOfUnitsEdit = new JTextField("7");
    private JTextField numOfIntervalsEdit = new JTextField("4");

    public PCSPGUI() {
        super(new BorderLayout());

        JPanel panel1 = new JPanel(new GridLayout(1, 6));
        panel1.add(new JLabel("Units #:"));
        panel1.add(this.numOfUnitsEdit);

        panel1.add(new JLabel("Intervals #:"));
        panel1.add(this.numOfIntervalsEdit);

        JButton setButton = new JButton("SET");
        panel1.add(setButton);

        JButton runButton = new JButton("RUN");
        panel1.add(runButton);
        add(panel1, BorderLayout.NORTH);

        this.tableModel = new ScheduleTableModel(4, 7);

        this.table = new JTable(this.tableModel);
        this.table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        this.table.setFillsViewportHeight(true);
        this.table.setTableHeader(null);
        this.table.setSelectionForeground(null);
        this.table.setSelectionBackground(null);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        this.table.setBackground(BK_COLOR);
        this.table.setDefaultRenderer(Object.class, new CellColorRenderer());

        this.setColumnWidth(this.table);

        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.getViewport().setBackground(BK_COLOR);

        // Add the scroll pane to this panel.
        add(scrollPane, BorderLayout.CENTER);

        setButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                table.setModel(new ScheduleTableModel(Integer.valueOf(numOfIntervalsEdit.getText()), Integer
                        .valueOf(numOfUnitsEdit.getText())));
                setColumnWidth(table);
                table.repaint();
            }
        });

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // table.setModel(new ScheduleTableModel(15, 20));
                // setColumnWidth(table);
                // table.repaint();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        resetPanel();

                        PCSP instance = PCSP.getInstance();
                        ScheduleState state = instance.run(getIntervals(), getCapacities(), getMaxLoads());
                        showIntervalNetReserves(state.getIntervalNetReserves());
                        showUnitScheduleState(state.getUnitScheduleState(), ScheduleState.getUnitIntervals());
                    }
                }).start();
            }
        });
    }

    private void setColumnWidth(JTable t) {
        TableColumn column = null;
        for (int i = 0; i < t.getColumnCount(); i++) {
            column = t.getColumnModel().getColumn(i);
            if (i < 3) {
                column.setPreferredWidth(80); // third column is bigger
            } else {
                column.setPreferredWidth(40);
            }
        }
    }

    private int[] getMaxLoads() {
        int col = this.table.getColumnCount();
        int[] maxLoads = new int[col - 3];
        for (int i = 3; i < col; i++) {
            maxLoads[i - 3] = Integer.valueOf((String) this.table.getModel().getValueAt(1, i));
        }
        System.out.println(Arrays.toString(maxLoads));
        return maxLoads;
    }

    private int[] getCapacities() {
        int row = this.table.getRowCount();
        int[] capacities = new int[row - 4];
        for (int i = 3; i < row - 1; i++) {
            capacities[i - 3] = Integer.valueOf((String) this.table.getModel().getValueAt(i, 1));
        }
        System.out.println(Arrays.toString(capacities));
        return capacities;
    }

    private int[] getIntervals() {
        int row = this.table.getRowCount();
        int[] intervals = new int[row - 4];
        for (int i = 3; i < row - 1; i++) {
            intervals[i - 3] = Integer.valueOf((String) this.table.getModel().getValueAt(i, 2));
        }
        System.out.println(Arrays.toString(intervals));
        return intervals;
    }

    private void showIntervalNetReserves(int[] intervalNetReserves) {
        int row = this.table.getRowCount();

        for (int i = 0; i < intervalNetReserves.length; i++) {
            this.table.getModel().setValueAt(String.valueOf(intervalNetReserves[i]), row - 1, i + 3);
        }
    }

    private void showUnitScheduleState(int[] unitScheduleState, int[] unitIntervals) {
        for (int i = 0; i < unitScheduleState.length; i++) {
            for (int j = 0; j < unitIntervals[i]; j++) {
                this.table.getModel().setValueAt("X", i + 3, 3 + unitScheduleState[i] + j);
            }
        }
    }

    private void resetPanel() {
        int col = this.table.getColumnCount();
        int row = this.table.getRowCount();

        for (int i = 3; i < row - 1; i++) {
            for (int j = 3; j < col; j++) {
                this.table.getModel().setValueAt("", i, j);
            }
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Maintenance Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        PCSPGUI newContentPane = new PCSPGUI();
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private class CellColorRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = -6999578905146410254L;

        public CellColorRenderer() {
            setOpaque(true);
            setHorizontalAlignment(JLabel.CENTER);
        }

        public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus,
                int row, int col) {
            Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);

            if ((row == 1 && col > 2) || ((3 <= row && row < t.getRowCount() - 1) && (col == 1 || col == 2))) {
                c.setBackground(Color.WHITE);
            } else if (row == 2 && (0 <= col && col <= 2)) {
                c.setBackground(new Color(128, 255, 128));// green color
            } else if ((3 <= row && row < t.getRowCount() - 1) && col == 0) {
                c.setBackground(new Color(180, 255, 180));// light green color
            } else if ((row == 0 || row == 1) && col == 2) {
                c.setBackground(new Color(128, 255, 255));// blue color
            } else if (row == 0 && col > 2) {
                c.setBackground(new Color(200, 255, 255));// light blue color
            } else if ((row == t.getRowCount() - 1) && col == 2) {
                c.setBackground(new Color(255, 255, 80));// yellow color
            } else if ((row == t.getRowCount() - 1) && col > 2) {
                c.setBackground(new Color(255, 255, 150));// light yellow color
            } else if ((3 <= row && row < t.getRowCount() - 1) && col > 2) {
                c.setBackground(new Color(238, 238, 238));// light gray color
            } else {
                c.setBackground(BK_COLOR);
            }

            if ("X".equals(String.valueOf(value))) {
                c.setBackground(Color.RED);
            }
            return c;
        }
    };
}