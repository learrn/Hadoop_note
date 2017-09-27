import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double[] x;
	private final int expTimes;

	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("Illeagal Argument");
		}
		x = new double[trials + 1];
		expTimes = trials;
		for (int i = 0; i < trials; i++) {
			Percolation perc = new Percolation(n);
			boolean[] isEmptySiteLine = new boolean[n + 1];
			int numOfLine = 0;
			while (true) {
				int posX, posY;
				do {
					posX = StdRandom.uniform(n) + 1;
					posY = StdRandom.uniform(n) + 1;
				} while (perc.isOpen(posX, posY));
				perc.open(posX, posY);
				x[i] += 1;
				if (!isEmptySiteLine[posX]) {
					isEmptySiteLine[posX] = true;
					numOfLine++;
				}
				if (numOfLine == n) {
					if (perc.percolates())
						break;
				}
			}
			x[i] = x[i] / (double) (n * n);
		}
	}

	public double mean() {
		return StdStats.mean(x);
	}

	public double stddev() {
		return StdStats.stddev(x);
	}

	public double confidenceLo() {
		double mu = mean();
		double sigma = stddev();
		return mu - 1.96 * sigma / Math.sqrt(expTimes);
	}

	public double confidenceHi() {
		double mu = mean();
		double sigma = stddev();
		return mu + 1.96 * sigma / Math.sqrt(expTimes);
	}

	public static void main(String[] args) {
		// test client (described below)
	}
}
