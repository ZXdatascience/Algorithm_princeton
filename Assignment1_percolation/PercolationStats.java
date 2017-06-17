import edu.princeton.cs.algs4.*;

public class PercolationStats{
	private double[] result;
	private int trials;
	public PercolationStats(int n, int trials){
		this.trials = trials;
		result = new double[trials];
		experiments(n);
	}
	
	private void experiments(int n){
		int numberOfTrial = 0;
		while(numberOfTrial < trials){
			Percolation percolation= new Percolation(n);
			double num = 0;
			int[] rowColNum;
			rowColNum= StdRandom.permutation(n*n);
			for(int i=0; i<n*n;i++){
					if(percolation.percolates()) break;
					num++;
					percolation.open(rowColNum[i]/n+1, rowColNum[i]%n+1);
					StdOut.println(percolation.numberOfOpenSites());
					}
			StdOut.println("percolate number" +num);
			result[numberOfTrial] = num/(n*n);
			numberOfTrial++;
			}
	}
	
	
	public double mean(){
		double sum = 0;
	    for (int i = 0; i < result.length; i++) {
	        sum += result[i];
	    }
	    return sum/result.length;
	}
	
	public double stddev(){
		double sd = 0;
		for (int i = 0; i < result.length; i++)
		{
			double average = mean();
		    sd += (result[i] - average)*(result[i] - average) / (result.length-1);
		}
		double standardDeviation = Math.sqrt(sd);
		return standardDeviation;
	}
	
	public double confidenceLo(){
		return mean() - stddev() * 1.96 / Math.sqrt(result.length);
	}
	
	public double confidenceHi(){
		return mean() + stddev() * 1.96 / Math.sqrt(result.length);
	}
	
	public static void main(String[] args){
		int input[] = new int[2];
		for (int i=0;i<2;i++){
			String item= StdIn.readString();
			input[i]= Integer.parseInt(item);
		}
		PercolationStats percolationStats= new PercolationStats(input[0], input[1]);
		StdOut.println("mean			= "+ percolationStats.mean());
		StdOut.println("stddev			= "+ percolationStats.stddev());
		StdOut.println("95% confidence interval	= "+ "["+percolationStats.confidenceLo()+", "+ percolationStats.confidenceHi()+ "]");
	}
	
}
