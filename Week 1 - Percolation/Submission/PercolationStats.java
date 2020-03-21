/* *****************************************************************************
 *  Name: Marcio TEDESCO
 *  Date: 20/03/2020
 *  Description: PercolationStats
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] percolationThresholds;
    private final int trials;
    private static final double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        //initialize instance fields
        percolationThresholds = new double[trials];
        this.trials = trials;

        //iterate
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            percolationThresholds[i] = percolation.numberOfOpenSites() / (double) (n * n);

            //System.out.print("Interaction number: " + i + ".)");
            //System.out.print("Number of opened sites: " + percolation.numberOfOpenSites());
            //System.out.println("Percolation threshold:" + percolationThresholds[i]);

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args[0] == null && args[1] == null)
            throw new IllegalArgumentException("arguments are invalid");

        //grid dimensions
        int n = Integer.parseInt(args[0]);

        //number of trials
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);

        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ","
                                   + percolationStats.confidenceHi() + "]");
    }

}
