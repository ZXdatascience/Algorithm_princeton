import java.util.NoSuchElementException;

public class Deque<Item> {
	private int Size;
	private Node first;
	private Node last;
	private class Node {
		Node next;
		Node previous;
		Item item;
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
    	Node oldFirst = first;
    	first = new Node();
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
    	Node oldLast = last;
    	last = new Node();
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

    public static void main(String[] args) {
	   // unit testing (optional)
    	Deque deque = new Deque();
    	deque.addFirst(5);
    	deque.addFirst(4);
    	System.out.println(deque.removeFirst());
    	deque.addLast(7);
    	System.out.println(deque.removeFirst());
    	System.out.println(deque.removeFirst());
    	
    }
}
