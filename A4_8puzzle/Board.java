import java.util.ArrayList;

import edu.princeton.cs.algs4.StdOut;


public class Board {
	private int hamming;
	private int manhattan;
	private int dim;
	private int[][] board;
	private int[] blankIndex= new int[2];
    public Board(int[][] blocks) {
    	// construct a board from an n-by-n array of blocks
    	dim = blocks.length;
    	board = new int[dim][dim];
    	for (int i = 0; i < dim; i++) {
    		for (int j = 0; j < dim; j++) {
        		this.board[i][j] = blocks[i][j];
        		}
    	}
		for (int i = 0; i < dim * dim; i++)  {
			int row = i / dim; int col = i % dim;
			
			if(board[row][col] != 0) {
				if (board[row][col] != i + 1) hamming++;
				manhattan += (Math.abs((board[row][col]-1) / dim - row) + Math.abs((board[row][col]-1) % dim  - (col)));
			}
			else {
				blankIndex[0] = row;
				blankIndex[1] = col;
    		}
			

    	}
    	
    }
                                           // (where blocks[i][j] = block in row i, column j)
    private int[][] neighbor(int[] move) {
    	int[][] neighbor = new int[dim][dim];
    	for (int i = 0; i < dim; i++) {
    		for (int j = 0; j < dim; j++) {
    			neighbor[i][j] = board[i][j];
    		}
    	}
    	int temp = neighbor[blankIndex[0] + move[0]][blankIndex[1] + move[1]];
    	neighbor[blankIndex[0] + move[0]][blankIndex[1] + move[1]] = 0;
    	neighbor[blankIndex[0]][blankIndex[1]] = temp;
    	return neighbor;
    }
    
    // move[] can only be -1,0,1, the first number means moving horizontally, the second number means moving vertically.

    public int dimension() {
    	// board dimension n
    	return dim;
    }
    public int hamming() {
    	// number of blocks out of place
    	return hamming;
    	
    }
    public int manhattan() {
    	// sum of Manhattan distances between blocks and goal
    	return manhattan;
    }
    public boolean isGoal() {
    	// is this board the goal board?
    	return hamming == 0;
    }
    public Board twin() {
    	// a board that is obtained by exchanging any pair of blocks
    	int[][] nonBlank = new int[2][2];
    	int count = 0;
    	Board twin = new Board(this.board);
    	try {
        	for (int i = 0; i < dim; i++) {
        		for (int j = 0; j < dim; j++) {
        			if (board[i][j] != 0) {
        				nonBlank[count][0] = i;
        				nonBlank[count][1] = j;
        				count++;
        			    }
            		}
        	}
    	}
    	catch (java.lang.IndexOutOfBoundsException e) {
    		int temp = twin.board[nonBlank[0][0]][nonBlank[0][1]];
    		twin.board[nonBlank[0][0]][nonBlank[0][1]] = twin.board[nonBlank[1][0]][nonBlank[1][1]];
    		twin.board[nonBlank[1][0]][nonBlank[1][1]] = temp;
        	
    	}
    	return twin;

    	
    }
    public boolean equals(Object y) {
    	// does this board equal y?
		if (this == y) {
			return true;
		}
		if (y == null) {
			return false;
		}
		if (this.getClass() != y.getClass()) {
			return false;
		}
		Board that = (Board) y;
		if (this.dim != that.dim) {
			return false;
		}
		for (int row = 0; row < dim; row++) {
			for (int col = 0; col < dim; col++) {
				if (this.board[row][col] != that.board[row][col]) {
					return false;
				}
			}
		}
        return true;

    }
    public Iterable<Board> neighbors() {
    	// all neighboring boards
    	int[][] direction = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
    	ArrayList<Board> neighborBoard = new ArrayList<Board>();
    	// up down left right is the order of the blank square's moving direction
    	// this order correspond to the neighbor boards in the neighborBoard list.
    	for (int i = 0; i <4; i++) {
    		try {
        		neighborBoard.add(new Board(neighbor(direction[i])));
        	}
    		catch (java.lang.IndexOutOfBoundsException e) {
    			
    		}
    	}
        return neighborBoard;
    }
    public String toString() {
    	// string representation of this board (in the output format specified below)
		String string = Integer.toString(dim);
		string += "\n";
    	for (int[] row : board) {
    		string += " ";
    		for (int num : row) {
    			String strI = Integer.toString(num);
    			string += (strI + "  ");
    		}
    		string = string.substring(0, string.length()- 1) + "\n";
    	}
    	return string.substring(0, string.length() - 1);
    }

    public static void main(String[] args) {
    	// unit tests (not graded)
    	int[][] board = {{1,2,3}, {4,6,5}, {7,0,8}};
    	Board b = new Board(board);
    	StdOut.println(b.toString());

    	
    }
}