import java.util.Arrays;
import edu.princeton.cs.algs4.*;

public class Percolation {
	private Site[][] grid;
	private Site topVirtualSite;
	private Site bottomVirtualSite;
	private int openNumber = 0;
	private int siteSize;
	private int[][] size;
	private class Site {
		private int row;
		private int col;
		private Site parent;
		private boolean isOpen;
		private Site(int row, int col){
			this.row = row;
			this.col = col;
			this.isOpen = false;
		}
		private boolean equals(Site other){
			return (this.row == other.row)&& (this.col== other.col);
		}
	}
	public Percolation(int n){
		// create n-by-n grid, with all sites blocked
		if (n <= 0) throw new java.lang.IllegalArgumentException();
		grid = new Site[n+2][n+2];
		size = new int[n+2][n+2];
		siteSize = n;
		for (int[] row: size)
		    Arrays.fill(row, 1);
		for(int i=0; i<n+2; i++){
			for(int j=0; j<n+2; j++){
				grid[i][j] = new Site(i,j);
				grid[i][j].parent= grid[i][j];
			}
		}
		topVirtualSite = grid[0][0];
		bottomVirtualSite = grid[n+1][n+1];
	}
	
	private void checkBound(int row, int col){
		if (row <= 0 || row > siteSize || col <= 0 || col > siteSize) throw new IndexOutOfBoundsException();
	}
	public void open(int row, int col){
		checkBound(row, col);
		if (row == 1) union(grid[row][col], topVirtualSite);
		if (row == siteSize) union(grid[row][col], bottomVirtualSite);
		grid[row][col].isOpen = true;
		openNumber++;
		if(grid[row+1][col].isOpen) union(grid[row][col], grid[row+1][col]);
		if(grid[row-1][col].isOpen) union(grid[row][col], grid[row-1][col]);
		if(grid[row][col+1].isOpen) union(grid[row][col], grid[row][col+1]);
		if(grid[row][col-1].isOpen) union(grid[row][col], grid[row][col-1]);
	}
	
	public boolean isOpen(int row, int col){
		checkBound(row, col);
		return grid[row][col].isOpen == true;
	}
	
	public boolean isFull(int row, int col){
		checkBound(row, col);
		return connected(grid[row][col], topVirtualSite);
	}
	
	public int numberOfOpenSites(){
		return openNumber;
	}
	
	public boolean percolates(){
		return connected(bottomVirtualSite, topVirtualSite);
	}
	
	private void union(Site p, Site q){
		 Site rootp = root(p);
		 Site rootq = root(q);
		 if (size[rootp.row][rootp.col] >= size[rootq.row][rootq.col]){
			 rootq.parent = rootp;
			 size[rootp.row][rootp.col] += size[rootq.row][rootq.col];
		 }
		 else{
			 rootp.parent = rootq;
			 size[rootq.row][rootq.col] += size[rootp.row][rootp.col];
		 }
	}
	
	private Site root(Site site){
		while (!site.equals(site.parent)){
			site = site.parent;
		}
		return site;
	}
	
	private boolean connected(Site p, Site q){
		return root(p).equals(root(q));
	}
	public static void main(String[] args){
		Percolation percolation= new Percolation(5);
		percolation.open(1, 1);
		percolation.open(1, 2);
		percolation.open(1, 3);
		percolation.open(2, 3);
		StdOut.println(percolation.openNumber);
		percolation.open(3, 3);
		percolation.open(4, 3);
		percolation.open(2, 2);
		StdOut.println(percolation.openNumber);
		if (percolation.isFull(5, 3)) StdOut.println("4, 3 in full");
		if (percolation.percolates()){
			System.out.println("The square is perculated");
		}
		else {
			System.out.println("The square is not perculated");
		}
	}
}
