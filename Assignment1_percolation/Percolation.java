import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.lang.*;
public class Percolation {
	private Site[][] grid;
	private Site topVirtualSite;
	private Site bottomVirtualSite;
        private int sizeSite;
	private int openNumber = 0;
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
			return (this.row == other.row)&& (this.col == other.col);
		}
	}
	public Percolation(int n){
		// create n-by-n grid, with all sites blocked
                if (n <= 0){
                    throw new IndexOutOfBoundsException();
                }
		grid = new Site[n][n];
		size = new int[n][n];
                sizeSite = n;
		for (int[] row: size)
		    Arrays.fill(row, 1);
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				grid[i][j] = new Site(i+1,j+1);
				grid[i][j].parent = grid[i][j];
			}
		}
		topVirtualSite = new Site(0, 0);
                topVirtualSite.parent = topVirtualSite;
		bottomVirtualSite = new Site(sizeSite + 1 , sizeSite + 1);
                bottomVirtualSite.parent = bottomVirtualSite;
	}
	private void checkIndex(int row, int col){
            if (row <= 0 || col <= 0 || row > sizeSite || col >  sizeSite){
                throw new IndexOutOfBoundsException();
            }
        }
	public void open(int row, int col){
                checkIndex(row, col);
                row--; col--;
                if(row ==0) union(grid[row][col], topVirtualSite);
                else if(row == sizeSite) union(grid[row][col], bottomVirtualSite);
		grid[row][col].isOpen = true;
		openNumber++;
                try{
                    if(grid[row+1][col].isOpen) union(grid[row][col], grid[row+1][col]);}
                catch(IndexOutOfBoundsException e){
                    StdOut.println("outofbounds");}
                
                try{
                    if(grid[row-1][col].isOpen) union(grid[row][col], grid[row-1][col]);}
                catch(IndexOutOfBoundsException e){
                    StdOut.println("outofbounds");}
                
                try{
                    if(grid[row][col+1].isOpen) union(grid[row][col], grid[row][col+1]);}
                catch(IndexOutOfBoundsException e){
                    StdOut.println("outofbounds");}
                
                try{
                    if(grid[row][col-1].isOpen) union(grid[row][col], grid[row][col-1]);}
                catch(IndexOutOfBoundsException e){
                    StdOut.println("outofbounds");}
	}
                
	
	public boolean isOpen(int row, int col){
                checkIndex(row, col);
                row--; col--;
		return grid[row][col].isOpen == true;
	}
	
	public boolean isFull(int row, int col){
                checkIndex(row, col);
                row--; col--;
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
		Percolation percolation = new Percolation(4);
		percolation.open(1, 1);
		percolation.open(1, 2);
		percolation.open(2, 2);
		percolation.open(2, 3);
		percolation.open(2, 4);
		if (percolation.percolates()){
		    System.out.println("The square is perculated");
		}
		else {
                    System.out.println("The square is not perculated");
		}
                if (percolation.isFull(2, 4)){
                    System.out.println("3,5 is Full");
                }
	}
}
