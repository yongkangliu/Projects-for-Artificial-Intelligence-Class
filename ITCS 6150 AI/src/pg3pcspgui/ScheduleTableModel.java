/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Final Project
 * 
 * by Yongkang Liu, 11/24/2012
 */
package pg3pcspgui;

import javax.swing.table.AbstractTableModel;

/**
 * The JTable data class. Store data of JTable.
 */
public class ScheduleTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 3939466186911495692L;

    // The table column names.
    private String[] columnNames;

    // The values of cells.
    private Object[][] data;

    /**
     * Constructor of ScheduleTableModel class
     * 
     * @param numOfIntervals
     *            The number of intervals.
     * @param numOfUnits
     *            The number of units.
     */
    public ScheduleTableModel(int numOfIntervals, int numOfUnits) {
        this.columnNames = new String[3 + numOfIntervals];
        this.data = new Object[4 + numOfUnits][3 + numOfIntervals];

        this.initializeObject();
    }

    /**
     * Initialize sample values
     */
    private void initializeObject() {
        for (int i = 0; i < this.columnNames.length; i++) {
            this.columnNames[i] = String.valueOf(i);
        }

        this.data[0][2] = "Interval Name";
        this.data[1][2] = "Max. loads (MW)";
        this.data[2][0] = "Unit Name";
        this.data[2][1] = "Capacity (MW)";
        this.data[2][2] = "Unit intervals";
        this.data[this.data.length - 1][2] = "Net reserve";

        for (int i = 3; i < this.data[0].length; i++) {
            this.data[0][i] = i - 2;
            this.data[1][i] = "80";
        }

        for (int i = 3; i < this.data.length - 1; i++) {
            this.data[i][0] = i - 2;
            this.data[i][1] = "15";
            this.data[i][2] = "1";
        }

        if (this.data.length >= 10 && columnNames.length >= 7) {
            // sample data
            this.data[1][3] = "80";
            this.data[1][4] = "90";
            this.data[1][5] = "65";
            this.data[1][6] = "70";

            this.data[3][1] = "20";
            this.data[3][2] = "2";
            this.data[4][1] = "15";
            this.data[4][2] = "2";
            this.data[5][1] = "35";
            this.data[5][2] = "1";
            this.data[6][1] = "40";
            this.data[6][2] = "1";
            this.data[7][1] = "15";
            this.data[7][2] = "1";
            this.data[8][1] = "15";
            this.data[8][2] = "1";
            this.data[9][1] = "10";
            this.data[9][2] = "1";
        }
    }

    /**
     * Return total number of column.
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Return total number of rows.
     */
    public int getRowCount() {
        return data.length;
    }

    /**
     * Get the value of a cell
     * 
     * @param row
     *            The row number in the table.
     * @param col
     *            The column number in the table.
     * @return Return the cell value.
     */
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /**
     * Check if the cell is editable.
     * 
     * @param row
     *            The row number in the table.
     * @param col
     *            The column number in the table.
     * @return Return true if the cell is editable, otherwise return false.
     */
    public boolean isCellEditable(int row, int col) {
        int numOfRows = this.data.length;

        if ((row == 1 && col > 2) || ((3 <= row && row < numOfRows - 1) && (col == 1 || col == 2))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the column name.
     * 
     * @param col
     *            The column
     * @return Return the column name.
     */
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Set value at a cell.
     * 
     * @param value
     *            The new value
     * @param row
     *            The row number in the table.
     * @param col
     *            The column number in the table.
     */
    public void setValueAt(Object value, int row, int col) {
        this.data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
