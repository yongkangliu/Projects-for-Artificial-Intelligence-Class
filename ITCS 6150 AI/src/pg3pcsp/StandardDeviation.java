/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Final Project
 * 
 * by Yongkang Liu, 11/24/2012
 */
package pg3pcsp;

/**
 * The mathematics formula of standard deviation
 */
public class StandardDeviation {

    // The array.
    private double[] data;

    // The length of the array.
    private int size;

    /**
     * Constructor for double array.
     * 
     * @param data
     *            The double array
     */
    public StandardDeviation(double[] data) {
        this.data = data;
        this.size = data.length;
    }

    /**
     * Constructor for integer array.
     * 
     * @param data
     *            The integer array
     */
    public StandardDeviation(int[] data) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i];
        }
        this.data = temp;
        this.size = data.length;
    }

    /**
     * Calculate mean value.
     * 
     * @return Return mean value.
     */
    private double getMean() {
        double sum = 0.0;
        for (double a : this.data) {
            sum += a;
        }
        return sum / this.size;
    }

    /**
     * Calculate variance value.
     * 
     * @return Return variance value.
     */
    private double getVariance() {
        double mean = getMean();
        double temp = 0;
        for (double a : this.data) {
            temp += (mean - a) * (mean - a);
        }
        return temp / this.size;
    }

    /**
     * Calculate standard deviation.
     * 
     * @return Return standard deviation value.
     */
    public double getStdDev() {
        return Math.sqrt(getVariance());
    }
}