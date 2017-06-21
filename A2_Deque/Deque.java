import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
	private int size;
	private Node first;
	private Node last;
	private class Node {
		private Node next;
		private Node previous;
		private Item item;
	}
    public Deque() {
	   // construct an empty deque
	    first = null;
	    last = null;
    }
    public boolean isEmpty() {
	   // is the deque empty?
    	return size == 0;
    }
    public int size() {
	   // return the number of items on the deque
    	return size;
    }
    public void addFirst(Item item) {
	   // add the item to the front
    	if (item == null) throw new NullPointerException();
    	Node oldFirst = first;
    	first = new Node();
    	first.item = item;
    	if (isEmpty()) {
    		last = first;
    		first.previous = null;
    		last.next = null;
    	}
    	else { 
    		first.next = oldFirst;
    		oldFirst.previous = first;
    	}
    	size++;
    }
    public void addLast(Item item) {
	   // add the item to the end
    	if (item == null) throw new NullPointerException();
    	Node oldLast = last;
    	last = new Node();
    	last.item = item;
    	last.next = null;
    	if (isEmpty()) {
    		first = last;
    		first.previous = null;
    		last.next = null;
    	}
    	else {
    		oldLast.next = last;
    		last.previous = oldLast;
    	}
    	size++;
    }
    public Item removeFirst() {
	   // remove and return the item from the front
    	if (isEmpty()) throw new NoSuchElementException();
    	else {
    		Item item = first.item;
        	if (last == first) {
        		first = null;
        		last = null;
        	}
        	else {
            	first = first.next;
            	first.previous = null;
        	}
        size--;
        return item;
    	}
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
        size--;
        return item;
    	}
    }
    public Iterator<Item> iterator() {
	   // return an independent iterator over items in random order
	    return new RandomizedQueueIterator();
    }
   
    private class RandomizedQueueIterator implements Iterator<Item> {
	    private Node current = first;
	   
	    public boolean hasNext() { return current != null; }
	    public void remove() { throw new UnsupportedOperationException(); }
	    public Item next() {
		    if (!hasNext()) throw new NoSuchElementException();
		    Item item = current.item;
		    current = current.next;
		    return item;
	    }
    }


    public static void main(String[] args) {
	   // unit testing (optional)
    	Deque<Integer> deque = new Deque<Integer>();
    	deque.addFirst(5);
    	deque.addFirst(4);
    	System.out.println(deque.removeFirst());
    	deque.addLast(7);
    	System.out.println(deque.removeFirst());
    	System.out.println(deque.removeFirst());
    	
    }
}