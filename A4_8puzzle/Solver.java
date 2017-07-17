import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private int isSolvable = 0;
	private LinkedBoard goalBoard;
    public Solver(Board initial) {
    	// find a solution to the initial board (using the A* algorithm)
    	if (initial == null) throw new java.lang.IllegalArgumentException();
    	MinPQ<LinkedBoard> pq= new MinPQ<LinkedBoard>(byPriority());
    	MinPQ<LinkedBoard> twinPQ = new MinPQ<LinkedBoard>(byPriority());
    	
    	LinkedBoard initialLinkedBoard = new LinkedBoard(initial);
    	LinkedBoard twinLinkedBoard = initialLinkedBoard.twin();
    	LinkedBoard current = initialLinkedBoard;
    	LinkedBoard currentTwin = twinLinkedBoard;
    	while(isSolvable == 0) {
    		if (current.board.isGoal()) {
    			isSolvable = 1;
    			goalBoard = current;
    		}
    		else if (currentTwin.board.isGoal()) {
    			isSolvable = -1;
    			goalBoard = null;
    		}
    		else {
	    		Iterable<LinkedBoard> neighbors = current.neighbors();
	    		Iterable<LinkedBoard> twinNeighbors = currentTwin.neighbors();
	    		for (LinkedBoard bo: neighbors) {

	    			if(current.previous == null || !bo.board.equals(current.previous.board)) {
	    				
		    			bo.moves = current.moves + 1;
		    			bo.priority = bo.board.manhattan() + bo.moves;
		    			pq.insert(bo);
		    			bo.previous = current;
	    			}
	    		}
	    	    current = pq.delMin();

    			
	    		for (LinkedBoard tBo: twinNeighbors) {
	    			if(!tBo.board.equals(currentTwin.previous.board)) {
		    			tBo.moves = currentTwin.moves + 1;
		    			tBo.priority = tBo.board.manhattan() + tBo.moves;
		    			twinPQ.insert(tBo);
		    			tBo.previous = currentTwin;
	    			}
	    		}
	    		currentTwin = twinPQ.delMin();
    	    }
    	}	

    }
    
    private class LinkedBoard {
    	private LinkedBoard previous;
    	private Board board;
    	private int  moves;
    	private int priority;
    	private LinkedBoard(Board board){
    		this.board = board;
    		this.previous = null;
    	}
    	private Iterable<LinkedBoard> neighbors() {
    		Iterable<Board> neighborBoards = board.neighbors();
    		ArrayList<LinkedBoard> neighborLinkedBoard = new ArrayList<LinkedBoard>();
    		for (Board bo : neighborBoards) {
    			LinkedBoard linkedBoard = new LinkedBoard(bo);
    			linkedBoard.previous = this;
    			neighborLinkedBoard.add(linkedBoard);
    		}
			return neighborLinkedBoard;
    	}
    	
    	private LinkedBoard twin() {
    		Board twinBoard = board.twin();
    		LinkedBoard twinLinkedBoard = new LinkedBoard(twinBoard);
    		twinLinkedBoard.previous = this;
    		return twinLinkedBoard;
    	}	
    }
    
    private Comparator<LinkedBoard> byPriority() {
        return new Comparator<LinkedBoard>() {
            @Override
            public int compare(LinkedBoard b1, LinkedBoard b2) {
                return Integer.compare(b1.priority, b2.priority);
                
            }
        };
    }

    public boolean isSolvable() {
    	// is the initial board solvable?
    	return isSolvable == 1;
    }
    public int moves() {
    	// min number of moves to solve initial board; -1 if unsolvable
    	if (isSolvable == -1) return -1;
    	else return goalBoard.moves;
    	
    }
    public Iterable<Board> solution() {
    	// sequence of boards in a shortest solution; null if unsolvable
		ArrayList<Board> solution = new ArrayList<Board>();
    	ArrayList<Board> solutionReversed = new ArrayList<Board>();
    	if (goalBoard == null) return null;
    	else {
    		LinkedBoard next = goalBoard;
    	    while (next != null) {
    	    	solution.add(next.board);
    	    	next = next.previous;
    	    }
    	}
    	for (int j = solution.size() - 1; j >= 0; j--) {
			solutionReversed.add(solution.get(j));
    	}
    	return solutionReversed;
    }
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}