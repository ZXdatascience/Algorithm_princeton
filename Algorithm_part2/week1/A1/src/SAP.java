
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private Digraph G;
	public SAP(Digraph G) {
		if(G == null) throw new java.lang.IllegalArgumentException();
		this.G = G;
	}
	
	private Map<Integer, Integer> pathToRoot(int v) {
		if(v>G.V()-1 ||v<0) throw new java.lang.IllegalArgumentException();
		Map<Integer, Integer> toRootMap = new HashMap<Integer, Integer>();
		Queue<Integer> vQ = new Queue<Integer>();
		vQ.enqueue(v);
		toRootMap.put(v, 0);
		int currentDist = 0;
		while(!vQ.isEmpty()) {
			int head = vQ.dequeue();
			currentDist = toRootMap.get(head);
			for(int w: G.adj(head)) {
				if (!toRootMap.containsKey(w) || toRootMap.get(w) > currentDist + 1) {
					toRootMap.put(w, currentDist + 1);
					vQ.enqueue(w);
				}
			}
		}
//		StdOut.print(toRootMap);
//		StdOut.print("\n");
		return toRootMap;
	}

	   // length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		if(v>G.V()-1 ||v<0) throw new java.lang.IllegalArgumentException();
		if(w>G.V()-1 ||w<0) throw new java.lang.IllegalArgumentException();
		Map<Integer, Integer> vToRoot = pathToRoot(v);
		Map<Integer, Integer> wToRoot = pathToRoot(w);
		int minDist = G.V();
		int ancester = -1;
		for (Integer k: wToRoot.keySet()) {
				if(vToRoot.containsKey(k)) {
					if(wToRoot.get(k) + vToRoot.get(k)< minDist) {
						minDist = wToRoot.get(k) + vToRoot.get(k);
						ancester = k;
					}
			}
		}
//		StdOut.print("ancester = ");
//		StdOut.print(ancester);
		if (ancester == -1) {
			return -1;
		}
		else {
			return minDist;
		}
	}

	
	   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		if(v>G.V()-1 ||v<0) throw new java.lang.IllegalArgumentException();
		if(w>G.V()-1 ||w<0) throw new java.lang.IllegalArgumentException();
		Map<Integer, Integer> vToRoot = pathToRoot(v);
		Map<Integer, Integer> wToRoot = pathToRoot(w);
		int minDist = G.V();
		int ancester = -1;
		for (Integer k: wToRoot.keySet()) {
				if(vToRoot.containsKey(k)) {
					if(wToRoot.get(k) + vToRoot.get(k)< minDist) {
						minDist = wToRoot.get(k) + vToRoot.get(k);
						ancester = k;
					}
			}
		}
//		StdOut.print("ancester = ");
//		StdOut.print(ancester);
		if (ancester == -1) {
			return -1;
		}
		else {
			return ancester;
		}
	}
//
	   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		int minDist = G.V();
		for(Integer vI: v) {
			for (Integer wI: w) {
				if (length(vI, wI) < minDist) {
					minDist = length(vI,wI);
				}
			}
		}
		if (minDist == G.V()) {
			return -1;
		}
		else{
			return minDist;
		}
	}

	   // a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		int minDist = G.V();
		int minV = -1;
		int minW = -1;
		for(Integer vI: v) {
			for (Integer wI: w) {
				if (length(vI, wI) < minDist) {
					minDist = length(vI,wI);
					minV = vI;
					minW = wI;
				}
			}
		}
		if(minDist == G.V()) {
			return -1;
		}
		else {
			return ancestor(minV, minW);
		}
	}
//
	   // do unit testing of this class
	public static void main(String[] args) {
	    In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
	}
		
}
