import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.UnsupportedOperationException;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class RandomizedQueue<Item> implements Iterable<Item> {
	private int head = 0;
	private Item[] q;
	private int size;
    public RandomizedQueue(int capacity) {
	   // construct an empty deque
        q = (Item[]) new Object[capacity];
    }
   
   public boolean isEmpty() {
	   // is the queue empty?
	   return size == 0;
   }
   
   public int size() {
	   // return the number of items on the queue
	   return size;
   }
   
   public void enqueue(Item item) {
	   // add the item
	   if (item == null) throw new NullPointerException();
	   if (size == q.length) resize(2 * q.length);
	   q[tail++] = item;
	   if tail
   }
   
   public Item dequeue() {
	   // remove and return a random item
	   if (isEmpty()) throw new NoSuchElementException();
	   Item item = q[head++];
	   q[head-1] = null;
	   if (size>0 && size == q.length/4) resize(q.length/2);
	   return item;
   }
   
   public Item sample() {
	   // return (but do not remove) a random item
	   int randInt = StdRandom.uniform(Size);
	   Node<Item> now;
	   if (randInt < Size/2) {
		   now = first;
		   for (int i = 0; i < randInt; i++) {
			   now = now.next;
		   }
	   }
	   else {
		   now = last;
		   for (int i = 0; i < Size - randInt; i++) {
			   now = now.previous;
		   }
	   }
	   return now.item;
   }
   
   public Iterator<Item> iterator() {
	   // return an independent iterator over items in random order
	   return new RandomizedQueueIterator(first);
   }
   
   private class RandomizedQueueIterator implements Iterator<Item> {
	   private Node<Item> current;
	   public RandomizedQueueIterator(Node<Item> first) {
		   current = first;
	   }
	   public boolean hasNext() {
		   return current!= null;
	   }
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
	   RandomizedQueue<String> randQue = new RandomizedQueue<String>();
	   for (int i = 0; i < 10; i++){
		   String item= StdIn.readString();
		   randQue.enqueue(item);
		}
   	Iterator<String> i = randQue.iterator();
	while (i.hasNext()) {
		String s= i.next();
		StdOut.println(s);
	}
   }
}
