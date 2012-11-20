package PG3PCSPGUI;

import javax.swing.table.AbstractTableModel;

public class ScheduleTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 3939466186911495692L;

    private String[] columnNames;
    private Object[][] data;

    public ScheduleTableModel(int numOfIntervals, int numOfUnits) {
        this.columnNames = new String[3 + numOfIntervals];
        this.data = new Object[4 + numOfUnits][3 + numOfIntervals];

        this.initializeObject();
    }

    private void initializeObject() {
        for (int i = 0; i < this.columnNames.length; i++) {
            this.columnNames[i] = String.valueOf(i);
        }

        this.data[0][2] = "Interval Name";
        this.data[1][2] = "Max. loads";
        this.data[2][0] = "Unit Name";
        this.data[2][1] = "Unit Capacity";
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

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public boolean isCellEditable(int row, int col) {
        int numOfRows = this.data.length;

        if ((row == 1 && col > 2) || ((3 <= row && row < numOfRows - 1) && (col == 1 || col == 2))) {
            return true;
        } else {
            return false;
        }
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public void setValueAt(Object value, int row, int col) {
        this.data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
