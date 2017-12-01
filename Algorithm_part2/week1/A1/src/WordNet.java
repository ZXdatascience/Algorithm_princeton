

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class WordNet{
	private int V;
//	private int E = 84505;
	private Map<String, List<Integer>> word2NumList;
	private Map<Integer, String> num2Word;
	private SAP s;
   // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if(synsets == null || hypernyms == null) throw new java.lang.IllegalArgumentException();
	    In br2 = new In(synsets);
	    String line2 = "";
	    word2NumList = new HashMap<String, List<Integer>>();
	    num2Word = new HashMap<Integer, String>();
        while ((line2 = br2.readLine()) != null) {
        		String[] split = line2.split(",");
        		String[] words = split[1].split(" ");
	        int	num = Integer.parseInt(split[0]);
	        List<Integer> numList = null;
	        for(String word: words) {
	        		if (word2NumList.containsKey(word)) {
	        			numList = word2NumList.get(word);
	        		}
	        		else {
	        			numList = new LinkedList<Integer>();
	        		}
	        		numList.add(num);
	        		word2NumList.put(word, numList);
	        }
	        
	        	String word = split[1];
	        	num2Word.put(num,word);
	        	this.V = Math.max(this.V, num);
        	}
		String line = "";
		In br = new In(hypernyms);
	    Digraph G = new Digraph(this.V+1);
	    while ((line = br.readLine()) != null) {
	    		List<Integer> hypernym = new ArrayList<Integer>();
	        	for (String s : line.split(",")) {
	        	    hypernym.add(Integer.parseInt(s));
	        	}
	        	int son = hypernym.get(0);
	        for (int parent: hypernym.subList(1, hypernym.size())) {
	        		G.addEdge(son, parent);
	        }
	    }
        if(!isDAG(G)) throw new java.lang.IllegalArgumentException();
    s = new SAP(G);
    }
    
    private Boolean dfs(Digraph G, int[] visit, int num) {
		HashSet<Integer> path = new HashSet<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(num);
		while (!stack.isEmpty()) {
			int item = stack.pop();
			visit[item] = 1;
			path.add(item);
			for (Integer ad:G.adj(item)) {
				if (visit[ad] == 0) {
					if (path.contains(ad)) {
						return false;
					}
					else {
						stack.push(ad);
					}
				}
			}
		}
		return true;
    }
    
    private Boolean isDAG(Digraph G) {
    		int[] visit = new int[G.V()];
    		for (int i = 0; i< G.V(); i++) {
    			Boolean containCycle = dfs(G, visit, i);
    			if (!containCycle) {
    				return false;
    			}
    		}
    		return true;
    }
   // returns all WordNet nouns
    public Iterable<String> nouns(){
	   return word2NumList.keySet();
    }

   // is the word a WordNet noun?
    public boolean isNoun(String word) {
    		if(word == null) throw new java.lang.IllegalArgumentException();
    	    return word2NumList.containsKey(word);
    }

   // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
    	   if(nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException();
	   return s.length(word2NumList.get(nounA), word2NumList.get(nounB));
    }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
 	   if(nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException();
	   return num2Word.get(s.ancestor(word2NumList.get(nounA), word2NumList.get(nounB)));
    }

   // do unit testing of this class
    public static void main(String[] args){
		String synsets = args[0];
		String hypernym = args[1];
		WordNet wr = new WordNet(synsets, hypernym);
		StdOut.print(wr.s.length(16, 388));
    }
}
