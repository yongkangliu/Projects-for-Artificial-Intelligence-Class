package PG3PCSPGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
    private JTextField restartTimes = new JTextField("500");

    private static JProgressBar bar;
    private static JTextArea logTextArea;

    public PCSPGUI() {
        super(new BorderLayout());

        JPanel panel1 = new JPanel(new GridLayout(1, 8));
        panel1.add(new JLabel("Restart #:"));
        panel1.add(this.restartTimes);

        panel1.add(new JLabel("Units #:"));
        panel1.add(this.numOfUnitsEdit);

        panel1.add(new JLabel("Intervals #:"));
        panel1.add(this.numOfIntervalsEdit);

        JButton setButton = new JButton("SET");
        panel1.add(setButton);

        JButton runButton = new JButton("RUN");
        panel1.add(runButton);

        JPanel panel2 = new JPanel(new GridLayout(1, 1));
        PCSPGUI.bar = new JProgressBar();
        PCSPGUI.bar.setMaximum(Integer.valueOf(this.restartTimes.getText()));
        PCSPGUI.bar.setStringPainted(true);
        panel2.add(PCSPGUI.bar);

        JPanel panelNorth = new JPanel(new GridLayout(2, 1));
        panelNorth.add(panel1);
        panelNorth.add(panel2);
        add(panelNorth, BorderLayout.NORTH);

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
        JScrollPane scrollPaneTable = new JScrollPane(this.table);
        scrollPaneTable.getViewport().setBackground(BK_COLOR);

        // Add the scroll pane to this panel.
        add(scrollPaneTable, BorderLayout.CENTER);

        setButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                bar.setMaximum(Integer.valueOf(restartTimes.getText()));
                bar.setValue(0);
                table.setModel(new ScheduleTableModel(Integer.valueOf(numOfIntervalsEdit.getText()), Integer
                        .valueOf(numOfUnitsEdit.getText())));
                setColumnWidth(table);
                table.repaint();
            }
        });

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        resetPanel();
                        Calendar startTime = Calendar.getInstance();

                        PCSP instance = PCSP.getInstance();
                        ScheduleState state = instance.run(getIntervals(), getCapacities(), getMaxLoads(),
                                Integer.valueOf(restartTimes.getText()));
                        if (state != null) {
                            showIntervalNetReserves(state.getIntervalNetReserves());
                            showUnitScheduleState(state.getUnitScheduleState(), ScheduleState.getUnitIntervals());
                        }
                        Calendar endTime = Calendar.getInstance();
                        PCSPGUI.appendLog("Time-consuming (seconds): "
                                + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);
                    }
                }).start();
            }
        });

        // add log area on SOUTH
        PCSPGUI.logTextArea = new JTextArea(5, 5);
        PCSPGUI.logTextArea.setLineWrap(true);
        JScrollPane scrollPaneLog = new JScrollPane(PCSPGUI.logTextArea);
        add(scrollPaneLog, BorderLayout.SOUTH);
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

        return maxLoads;
    }

    private int[] getCapacities() {
        int row = this.table.getRowCount();
        int[] capacities = new int[row - 4];
        for (int i = 3; i < row - 1; i++) {
            capacities[i - 3] = Integer.valueOf((String) this.table.getModel().getValueAt(i, 1));
        }

        return capacities;
    }

    private int[] getIntervals() {
        int row = this.table.getRowCount();
        int[] intervals = new int[row - 4];
        for (int i = 3; i < row - 1; i++) {
            intervals[i - 3] = Integer.valueOf((String) this.table.getModel().getValueAt(i, 2));
        }

        return intervals;
    }

    private void showIntervalNetReserves(int[] intervalNetReserves) {
        int row = this.table.getRowCount();
        int col = this.table.getColumnCount();

        if ((col - 3) == intervalNetReserves.length) {

            for (int i = 0; i < intervalNetReserves.length; i++) {
                this.table.getModel().setValueAt(String.valueOf(intervalNetReserves[i]), row - 1, i + 3);
            }
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
        PCSPGUI.resetLog("Initializing screen ...");
        int col = this.table.getColumnCount();
        int row = this.table.getRowCount();

        for (int i = 3; i < row; i++) {
            for (int j = 3; j < col; j++) {
                this.table.getModel().setValueAt("", i, j);
            }
        }
    }

    public static void showProgress(int num) {
        if (PCSPGUI.bar != null) {
            PCSPGUI.bar.setValue(num);
        }
    }

    /**
     * Append information in the EditText.
     * 
     * @param str
     *            the information
     */
    public static void appendLog(String str) {
        if (PCSPGUI.logTextArea != null) {
            PCSPGUI.logTextArea.append(str);
            PCSPGUI.logTextArea.append("\r\n");
            PCSPGUI.logTextArea.setCaretPosition(logTextArea.getDocument().getLength() - 1);
        } else {
            System.out.println(str);
        }
    }

    /**
     * Clean old information and print new information in the EditText.
     * 
     * @param str
     *            the new information
     */
    public static void resetLog(String str) {
        if (PCSPGUI.logTextArea != null) {
            PCSPGUI.logTextArea.setText(str);
            PCSPGUI.logTextArea.append("\r\n");
            PCSPGUI.logTextArea.repaint();
        } else {
            System.out.println(str);
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