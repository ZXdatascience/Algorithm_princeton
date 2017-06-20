
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.UnsupportedOperationException;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class Deque<Item> implements Iterable<Item> {
	private int Size;
	private Node<Item> first;
	private Node<Item> last;
	private static class Node<Item> {
		Node<Item> next;
		Node<Item> previous;
		private Item item;
	}
    public Deque() {
	   // construct an empty deque
	    first = null;
	    last = null;
    }
    public boolean isEmpty() {
	   // is the deque empty?
    	return Size == 0;
    }
    public int size() {
	   // return the number of items on the deque
    	return Size;
    }
    public void addFirst(Item item) {
	   // add the item to the front
    	if (item == null) throw new NullPointerException();
    	Node<Item> oldFirst = first;
    	first = new Node<Item>();
    	first.item = item;
    	if (isEmpty()) {
    		last = first;
    		last.next = null;
    	}
    	else { 
    		first.next = oldFirst;
    		oldFirst.previous = first;
    	}
    	Size++;
    }
    public void addLast(Item item) {
	   // add the item to the end
    	if (item == null) throw new NullPointerException();
    	Node<Item> oldLast = last;
    	last = new Node<Item>();
    	last.item = item;
    	last.next = null;
    	if (isEmpty()) first = last;
    	else {
    		oldLast.next = last;
    		last.previous = oldLast;
    	}
    	Size++;
    }
    public Item removeFirst() {
	   // remove and return the item from the front
    	if (isEmpty()) throw new NoSuchElementException();
    	Item item = first.item;
    	first = first.next;
    	if (first == null) last = null;
    	Size--;
    	return item;
    }
    public Item removeLast() {
	   // remove and return the item from the end
    	if (isEmpty()) throw new NoSuchElementException();
    	else {
    		Item item = last.item;
        	if (last == first) {
        		first = null;
        		last = null;
        	}
        	else {
        		last = last.previous;
            	last.next = null;
        	}
        return item;
    	}

    	
    }
    
    public Iterator<Item> iterator() {
    	return new DequeIterator(first);
    }
    
    private class DequeIterator implements Iterator<Item> {
    	private Node<Item> current;
        public DequeIterator(Node<Item> first) {
            current = first;
        }
    	public boolean hasNext() { return current != null;}
    	public void remove() {throw new UnsupportedOperationException();}
    	
    	public Item next() {
    		if(!hasNext()) throw new NoSuchElementException();
    		Item item = current.item;
    		current = current.next;
    		return item;
    	}
    }
    public static void main(String[] args) {
	   // unit testing (optional)
    	Deque<Integer> deque = new Deque<Integer>();
	    for (int i = 0; i < 10; i++){
		   Integer item= StdIn.readInt();
		   deque.addFirst(item);
		}
    	Iterator<Integer> i = deque.iterator();
    	while (i.hasNext()) {
    		Integer s= i.next();
    		StdOut.println(s);
    	}
    	
    }
}