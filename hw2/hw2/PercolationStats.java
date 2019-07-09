package hw2;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        double[] sampleValues = new double[T];
        double totalNumberOfSites = N * N;
        for (int i = 0; i< T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                OpenRandomSites(p, N);
            }
            sampleValues[i] = p.numberOfOpenSites() / totalNumberOfSites;
            mean += sampleValues[i];
        }
        mean /= T;
        calculateStdDev(sampleValues, T);
        confidenceLow = mean - 1.96 * stddev / Math.sqrt(T);
        confidenceHigh = mean + 1.96 * stddev / Math.sqrt(T);
    }
    private void OpenRandomSites(Percolation p, int N) {
        int row, col;
        do {
            row = StdRandom.uniform(N);
            col = StdRandom.uniform(N);
        } while (p.isOpen(row, col));
        p.open(row, col);
    }
    private void calculateStdDev(double[] sampleValues, int T) {
        for (int i = 0; i < T; i++) {
            stddev += Math.pow(sampleValues[i] - mean, 2);
        }
        stddev /= (T - 1);
        stddev = Math.sqrt(stddev);
    }
    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow(){
        return confidenceLow;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return confidenceHigh;
    }
}
