package PG3PCSP;

import java.util.Arrays;

public class StandardDeviation {
    private double[] data;
    private int size;

    public StandardDeviation(double[] data) {
        this.data = data;
        this.size = data.length;
    }

    public StandardDeviation(int[] data) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i];
        }
        this.data = temp;
        this.size = data.length;
    }

    private double getMean() {
        double sum = 0.0;
        for (double a : this.data) {
            sum += a;
        }
        return sum / this.size;
    }

    private double getVariance() {
        double mean = getMean();
        double temp = 0;
        for (double a : this.data) {
            temp += (mean - a) * (mean - a);
        }
        return temp / this.size;
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }

    public double median() {
        double[] b = new double[this.data.length];
        System.arraycopy(this.data, 0, b, 0, b.length);
        Arrays.sort(b);

        if (this.data.length % 2 == 0) {
            return (b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0;
        } else {
            return b[b.length / 2];
        }
    }
}