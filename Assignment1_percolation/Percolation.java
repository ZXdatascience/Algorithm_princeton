import java.util.Arrays;

public class Percolation {
	private Site[][] grid;
	private Site topVirtualSite;
	private Site bottomVirtualSite;
	private int openNumber= 0;
	private int[][] size;
	private class Site {
		private int row;
		private int col;
		private Site parent;
		private boolean isOpen;
		private Site(int row, int col){
			this.row= row;
			this.col= col;
			this.isOpen= false;
		}
		private boolean equals(Site other){
			return (this.row== other.row)&& (this.col== other.col);
		}
	}
	public Percolation(int n){
		// create n-by-n grid, with all sites blocked
		grid= new Site[n+2][n+2];
		size= new int[n+2][n+2];
		for (int[] row: size)
		    Arrays.fill(row, 1);
		for(int i=0; i<n+2; i++){
			for(int j=0; j<n+2; j++){
				grid[i][j]= new Site(i,j);
				grid[i][j].parent= grid[i][j];
			}
		}
		topVirtualSite= grid[0][0];
		bottomVirtualSite = grid[n+1][n+1];
		for (int i=1; i<n+1; i++){
			union(grid[1][i], topVirtualSite);
			union(grid[n][i], bottomVirtualSite);
		}
	}
	
	public void open(int row, int col){
		grid[row][col].isOpen= true;
		openNumber++;
		if(grid[row+1][col].isOpen) union(grid[row][col], grid[row+1][col]);
		if(grid[row-1][col].isOpen) union(grid[row][col], grid[row-1][col]);
		if(grid[row][col+1].isOpen) union(grid[row][col], grid[row][col+1]);
		if(grid[row][col-1].isOpen) union(grid[row][col], grid[row][col-1]);
	}
	
	public boolean isOpen(int row, int col){
		return grid[row][col].isOpen==true;
	}
	
	public boolean isFull(int row, int col){
		return connected(grid[row][col], topVirtualSite);
	}
	
	public int numberOfOpenSites(){
		return openNumber;
	}
	
	public boolean percolates(){
		return connected(bottomVirtualSite, topVirtualSite);
	}
	
	private void union(Site p, Site q){
		 Site rootp= root(p);
		 Site rootq= root(q);
		 if (size[rootp.row][rootp.col]>= size[rootq.row][rootq.col]){
			 rootq.parent= rootp;
			 size[rootp.row][rootp.col]+= size[rootq.row][rootq.col];
		 }
		 else{
			 rootp.parent= rootq;
			 size[rootq.row][rootq.col]+= size[rootp.row][rootp.col];
		 }
	}
	
	private Site root(Site site){
		while (!site.equals(site.parent)){
			site= site.parent;
		}
		return site;
	}
	
	private boolean connected(Site p, Site q){
		return root(p).equals(root(q));
	}
	public static void main(String[] args){
		Percolation percolation= new Percolation(5);
		percolation.open(1, 1);
		percolation.open(2, 1);
		percolation.open(3, 1);
		percolation.open(4, 1);
		percolation.open(5, 5);
		if (percolation.percolates()){
			System.out.println("The square is perculated");
		}
		else {
			System.out.println("The square is not perculated");
		}
	}
}
